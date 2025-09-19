package com.example.demo.domain.group;

import com.example.demo.domain.group.dto.GroupCreateDTO;
import com.example.demo.domain.group.dto.GroupDTO;
import com.example.demo.domain.group.dto.GroupMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupController(GroupService groupService, GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('GROUP_CREATE')")
    @Operation(summary = "Create a new group")
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupCreateDTO dto) {
        Group group = groupService.createGroup(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(groupMapper.toDTO(group));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable UUID id, @Valid @RequestBody GroupCreateDTO dto) {
        Group updatedGroup = groupService.updateGroup(id, dto);
        return ResponseEntity.ok(groupMapper.toDTO(updatedGroup));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

}
