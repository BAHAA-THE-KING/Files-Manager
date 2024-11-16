package com.w.ever.files.manager.dto.groups;

import com.w.ever.files.manager.models.GroupModel;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.validation.Exists;
import com.w.ever.files.manager.validation.Unique;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateGroupDTO {

    @NotNull(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    @Unique(entity = GroupModel.class, fieldName = "name", message = "Group name is used")
    private String name;

    @NotNull(message = "Creator Id is required")
    @Exists(entity = UserModel.class, fieldName = "id", message = "Creator ID does not exist")
    private Integer creator_id;

    private String description;
    private String color;
    private String lang;
}
