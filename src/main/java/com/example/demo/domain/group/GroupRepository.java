package com.example.demo.domain.group;

import com.example.demo.core.generic.AbstractRepository;
import java.util.UUID;

public interface GroupRepository extends AbstractRepository<Group> {
    boolean existsByName(String name);
}

