package com.example.demo.domain.group.dto;

import com.example.demo.core.generic.AbstractDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class GroupCreateDTO extends AbstractDTO {
    @NotNull
    @Size(min = 3, max = 100, message = "The group name must be between {min} and {max} characters long.")
    private String name;
    private String motto;
    @Pattern(regexp = ".*\\.(png|jpg)$", message = "Logo must be a PNG or JPG file")
    private String logo;
    @NotNull
    private UUID administratorId;
    private Set<UUID> memberIds;
}
