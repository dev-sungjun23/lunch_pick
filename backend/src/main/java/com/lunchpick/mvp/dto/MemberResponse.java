package com.lunchpick.mvp.dto;

public class MemberResponse {

    private Long id;
    private String name;
    private Long teamId;

    public MemberResponse() {
    }

    public MemberResponse(Long id, String name, Long teamId) {
        this.id = id;
        this.name = name;
        this.teamId = teamId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getTeamId() {
        return teamId;
    }
}
