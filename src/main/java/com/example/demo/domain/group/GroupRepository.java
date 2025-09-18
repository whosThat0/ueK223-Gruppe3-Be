package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractRepository;

public interface GroupRepository extends AbstractRepository<Group> {
    boolean existsByName(String name);
}

