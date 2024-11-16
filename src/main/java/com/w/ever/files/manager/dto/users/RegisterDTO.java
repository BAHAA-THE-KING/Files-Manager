package com.w.ever.files.manager.dto.users;

import com.w.ever.files.manager.models.UserModel;
import com.w.ever.files.manager.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class RegisterDTO {
    @NotNull(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotNull(message = "Username is required")
    @Size(min = 5, message = "Username must be at least 5 characters long")
    @Unique(entity = UserModel.class, fieldName = "username", message = "Username is used")
    private String username;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    @Unique(entity = UserModel.class, fieldName = "email", message = "Email is used")
    private String email;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long and include at least one letter, one number, and one special character")
    private String password;
}
