package com.w.ever.files.manager.dto.groups;

import com.w.ever.files.manager.models.GroupModel;
import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.validation.Exists;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GroupInvitationDTO {
    @NotNull(message = "User Id is required")
    @Exists(entity = UserModel.class, fieldName = "id", message = "User ID does not exist")
    private Integer user_id;

    @NotNull(message = "Group Id is required")
    @Exists(entity = GroupModel.class, fieldName = "id", message = "Group ID does not exist")
    private Integer group_id;
}
