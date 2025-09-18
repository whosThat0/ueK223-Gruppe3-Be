package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractService;
import com.example.demo.domain.group.dto.GroupCreateDTO;

public interface GroupService extends AbstractService<Group> {
    Group createGroup(GroupCreateDTO dto);
}

