package com.w.ever.files.manager.dto.files;

import com.w.ever.files.manager.models.GroupModel;
import com.w.ever.files.manager.validation.Exists;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFolderRequestDTO {
    @NotNull(message = "Group Id is required")
    @Exists(entity = GroupModel.class, fieldName = "id", message = "Group ID does not exist")
    private Integer groupId;

    @NotNull(message = "Path is required")
    private String path;
}
