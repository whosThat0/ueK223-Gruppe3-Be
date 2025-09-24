package com.example.demo.domain.group;

import com.example.demo.domain.group.dto.GroupCreateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Group> getAllGroups() {
        return groupService.findAllGroups();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Group getGroup(@PathVariable UUID id) {
        return groupService.findGroupById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('GROUP_CREATE')")
    public Group createGroup(@Valid @RequestBody GroupCreateDTO dto) {
        return groupService.createGroup(dto);
    }

    @PostMapping("/{groupId}/join")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> joinGroup(@PathVariable UUID groupId, @RequestParam UUID userId) {
        groupService.joinGroup(userId, groupId);
        return ResponseEntity.ok("User joined group successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_CREATE')")
    public Group updateGroup(@PathVariable UUID id, @RequestBody GroupCreateDTO dto) {
        return groupService.updateGroup(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
    }
}
