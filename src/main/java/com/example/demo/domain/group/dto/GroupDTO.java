package com.example.demo.domain.group.dto;

import com.example.demo.core.generic.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO extends AbstractDTO {
    private String name;
    private String motto;
    private String logo;
    private UUID administratorId;
    private Set<UUID> memberIds;
}
