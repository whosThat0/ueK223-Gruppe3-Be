package com.example.demo.domain.group.dto;

import com.example.demo.domain.group.Group;
import com.example.demo.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @Mapping(source = "administrator.id", target = "administratorId")
    @Mapping(source = "members", target = "memberIds")
    GroupDTO toDTO(Group group);

    default Set<UUID> map(Set<User> members) {
        if (members == null) return new HashSet<>();
        return members.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}

