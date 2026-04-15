package com.lunchpick.mvp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RecommendationRequest {

    @NotNull
    private Long teamId;

    @NotBlank
    private String location;

    @NotNull
    private Integer maxDistance;

    @NotNull
    private Integer budget;

    private Double latitude;

    private Double longitude;

    public RecommendationRequest() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getLocation() {
        return location;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
