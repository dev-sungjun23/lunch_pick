package com.lunchpick.mvp.dto;

import jakarta.validation.constraints.NotBlank;

public class TeamCreateRequest {

    @NotBlank
    private String name;

    public TeamCreateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
