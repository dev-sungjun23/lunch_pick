package com.lunchpick.mvp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MemberCreateRequest {

    @NotNull
    private Long teamId;

    @NotBlank
    private String name;

    public MemberCreateRequest() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
