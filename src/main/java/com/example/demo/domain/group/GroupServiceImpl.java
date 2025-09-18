package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractServiceImpl;
import com.example.demo.domain.group.dto.GroupCreateDTO;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
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
        User admin = userService.findById(dto.getAdministratorId());
        Set<User> members = new HashSet<>();
        if (groupRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Group name already exists: " + dto.getName());
        }
        if (dto.getMemberIds() != null) {
            members = dto.getMemberIds()
                    .stream()
                    .map(userService::findById)
                    .collect(Collectors.toSet());
        }
        Group group = new Group()
                .setName(dto.getName())
                .setMotto(dto.getMotto())
                .setLogo(dto.getLogo())
                .setAdministrator(admin)
                .setMembers(members);
        return groupRepository.save(group);
    }
}

