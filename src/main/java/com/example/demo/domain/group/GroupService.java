package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractService;
import com.example.demo.domain.group.dto.GroupCreateDTO;

import java.util.List;
import java.util.UUID;

public interface GroupService extends AbstractService<Group> {
    List<Group> findAllGroups();
    Group findGroupById(UUID id);

    Group createGroup(GroupCreateDTO dto);

    Group updateGroup(UUID id, GroupCreateDTO dto);

    void deleteGroup(UUID id);
}
