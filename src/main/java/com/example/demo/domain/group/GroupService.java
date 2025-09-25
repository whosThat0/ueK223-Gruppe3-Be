package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractService;
import com.example.demo.domain.group.dto.GroupCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GroupService extends AbstractService<Group> {

    Page<Group> findAllGroups(Pageable pageable);

    Group findGroupById(UUID id);

    Group createGroup(GroupCreateDTO dto);

    Group updateGroup(UUID id, GroupCreateDTO dto);

    void deleteGroup(UUID id);
    void joinGroup(UUID userId);
}
