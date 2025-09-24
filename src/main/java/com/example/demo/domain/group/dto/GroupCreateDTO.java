package com.example.demo.domain.group.dto;

import com.example.demo.core.generic.AbstractDTO;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Status: 400, Name cannot be empty. Please enter between 3 and 100 characters.")
    @Size(min = 3, max = 100, message = "The group name must be between {min} and {max} characters long.")
    private String name;
    private String motto;
    @Pattern(regexp = ".*\\.(png|jpg)$", message = "Logo must be a PNG or JPG file. Uppercase extensions like .PNG or .JPG are not allowed.")
    private String logo;
    private Set<UUID> memberIds;
}
