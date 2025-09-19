package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractService;
import com.example.demo.domain.group.dto.GroupCreateDTO;

import java.util.UUID;

public interface GroupService extends AbstractService<Group> {
    Group createGroup(GroupCreateDTO dto);
    Group updateGroup(UUID groupId, GroupCreateDTO dto);
    void deleteGroup(UUID groupId);
}
