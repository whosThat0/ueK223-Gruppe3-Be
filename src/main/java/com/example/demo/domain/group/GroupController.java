package com.example.demo.domain.group;

import com.example.demo.domain.group.dto.GroupCreateDTO;
import com.example.demo.domain.group.dto.GroupPageDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
// 1. Return the new DTO instead of the raw Page object
    public GroupPageDTO getAllGroups(Pageable pageable) {
        Page<Group> groupPage = groupService.findAllGroups(pageable);

        // 2. Wrap the Spring Page object in your custom DTO
        return new GroupPageDTO(groupPage);
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

    @PostMapping("/{groupId}/join") // No query parameter needed
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> joinGroup(@PathVariable UUID groupId) {
        groupService.joinGroup(groupId);
        return ResponseEntity.ok("User joined group successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_CREATE')")
    public Group updateGroup(@PathVariable UUID id, @Valid @RequestBody GroupCreateDTO dto) {
        return groupService.updateGroup(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('GROUP_DELETE')")
    public ResponseEntity<Map<String, String>> deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
        Map<String, String> response = Map.of("message", "Group deleted successfully");
        return ResponseEntity.ok(response);
    }

}