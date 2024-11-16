package com.w.ever.files.manager.dto.groups;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateGroupDTO {

    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    private String description;
    private String color;
    private String lang;
}
