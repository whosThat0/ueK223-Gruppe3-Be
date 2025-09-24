package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractServiceImpl;
import com.example.demo.domain.group.dto.GroupCreateDTO;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl extends AbstractServiceImpl<Group> implements GroupService {
    private final GroupRepository groupRepository;
    private final UserService userService;
    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserService userService) {
        super(groupRepository);
        this.groupRepository = groupRepository;
        this.userService = userService;
    }
    @Override
    public Group createGroup(GroupCreateDTO dto) {
        if (groupRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Group name already exists: " + dto.getName());
        }

        User admin = userService.getCurrentAuthenticatedUser();
        Set<User> members = mapMembers(dto.getMemberIds());

        return groupRepository.save(new Group()
                .setName(dto.getName())
                .setMotto(dto.getMotto())
                .setLogo(dto.getLogo())
                .setAdministrator(admin)
                .setMembers(members));
    }

    private Set<User> mapMembers(Set<UUID> memberIds) {
        if (memberIds == null) return Collections.emptySet();
        return memberIds.stream()
                .map(userService::findById)
                .collect(Collectors.toSet());
    }
}

