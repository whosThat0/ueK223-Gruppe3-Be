package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractServiceImpl;
import com.example.demo.domain.group.dto.GroupCreateDTO;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Required for the new method
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    // ***************************************************************
    // ✅ FIX: Implement the required abstract method from GroupService
    // ***************************************************************
    @Override
    public Page<Group> findAllGroups(Pageable pageable) {
        // Assuming your GroupRepository extends JpaRepository<Group, UUID>,
        // it automatically provides the findAll(Pageable) method.
        return groupRepository.findAll(pageable);
    }

    // ***************************************************************
    // The rest of your existing methods follow
    // ***************************************************************

    private Set<User> mapMembers(Set<UUID> memberIds) {
        if (memberIds == null || memberIds.isEmpty()) {
            return new HashSet<>();
        }

        Set<User> users = new java.util.HashSet<>();
        for (UUID id : memberIds) {
            User user = userService.findById(id);
            users.add(user);
            user.setGroup(null); // NOTE: This looks like it might incorrectly reset the group for the user being added. You should review this logic.
        }
        return users;
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

    @Override // Note: You need the @Override annotation here
    public void joinGroup(UUID groupId) {
        User user = userService.getCurrentAuthenticatedUser();

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