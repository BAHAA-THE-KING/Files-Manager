package com.w.ever.files.manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TestSearchDTO {

    @NotNull(message = "Search term cannot be null")
    @Size(min = 1, message = "Search term must be at least 1 character long")
    private String searchTerm;

    // Getters and Setters
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
