package com.w.ever.files.manager.dto.files;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ReserveFilesDTO {
    @NotNull(message = "The files ids cannot be null")
    @NotEmpty(message = "The files ids list cannot be empty")
    private List<Integer> filesIds;
}
