package com.example.demo.domain.group;

import com.example.demo.domain.group.dto.GroupCreateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public List<Group> getAllGroups() {
        return groupService.findAllGroups();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable UUID id) {
        return groupService.findGroupById(id);
    }

    @PostMapping
    public Group createGroup(@Valid @RequestBody GroupCreateDTO dto) {
        return groupService.createGroup(dto);
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<String> joinGroup(@PathVariable UUID groupId, @RequestParam UUID userId) {
        groupService.joinGroup(userId, groupId);
        return ResponseEntity.ok("User joined group successfully");
    }

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable UUID id, @RequestBody GroupCreateDTO dto) {
        return groupService.updateGroup(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
    }
}
