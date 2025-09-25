package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractServiceImpl;
import com.example.demo.domain.group.dto.GroupCreateDTO;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class GroupServiceImpl extends AbstractServiceImpl<Group> implements GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    private static final String GROUP_NOT_FOUND = "Group not found with id: ";

    public GroupServiceImpl(GroupRepository groupRepository, UserService userService) {
        super(groupRepository);
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    private Set<User> mapMembers(Set<UUID> memberIds) {
        if (memberIds == null || memberIds.isEmpty()) {
            return new HashSet<>();
        }

        Set<User> users = new java.util.HashSet<>();
        for (UUID id : memberIds) {
            User user = userService.findById(id);
            users.add(user);
            user.setGroup(null);
        }
        return users;
    }

    @Override
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group findGroupById(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException(GROUP_NOT_FOUND + groupId));
    }

    @Override
    public Group createGroup(GroupCreateDTO dto) {
        User admin = userService.getCurrentAuthenticatedUser();

        boolean canCreate = admin.getRoles().stream()
                .flatMap(role -> role.getAuthorities().stream())
                .anyMatch(auth -> "GROUP_CREATE".equals(auth.getName()));

        if (!canCreate) {
            throw new AccessDeniedException("You do not have permission to create groups");
        }

        if (groupRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Group name already exists: " + dto.getName());
        }

        Set<User> members = mapMembers(dto.getMemberIds());

        Group group = new Group()
                .setName(dto.getName())
                .setMotto(dto.getMotto())
                .setLogo(dto.getLogo())
                .setAdministrator(admin)
                .setMembers(members);

        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(UUID groupId, GroupCreateDTO dto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException(GROUP_NOT_FOUND + groupId));
        if (!group.getName().equals(dto.getName()) && groupRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Group name already exists: " + dto.getName());
        }
        group.setName(dto.getName());
        group.setMotto(dto.getMotto());
        group.setLogo(dto.getLogo());

        if (dto.getMemberIds() != null) {
            group.setMembers(mapMembers(dto.getMemberIds()));
        }
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException(GROUP_NOT_FOUND + groupId));
        group.getMembers().forEach(member -> member.setGroup(null));
        groupRepository.delete(group);
    }

    public void joinGroup(UUID groupId) {
        User user = userService.getCurrentAuthenticatedUser(); // Use the existing method
        UUID userId = user.getId();

        // 2. The rest of the logic remains the same
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException(GROUP_NOT_FOUND + groupId));

        if (user.getGroup() != null) {
            throw new IllegalArgumentException("User is already part of a group: " + user.getGroup().getName());
        }
        user.setGroup(group);
        group.getMembers().add(user);
        groupRepository.save(group);
    }
}
