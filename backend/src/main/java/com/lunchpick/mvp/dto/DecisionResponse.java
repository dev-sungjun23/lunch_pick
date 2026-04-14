package com.lunchpick.mvp.dto;

public class DecisionResponse {

    private Long teamId;
    private Long restaurantId;
    private String restaurantName;
    private String reason;

    public DecisionResponse() {
    }

    public DecisionResponse(Long teamId, Long restaurantId, String restaurantName, String reason) {
        this.teamId = teamId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.reason = reason;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getReason() {
        return reason;
    }
}
