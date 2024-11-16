package com.w.ever.files.manager.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginDTO {
    @NotNull(message = "Username is required")
    @Size(min = 1, max = 50, message = "Username must be between 1,50 characters")
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 1, max = 100, message = "Password must be between 1,50 characters")
    private String password;
}
