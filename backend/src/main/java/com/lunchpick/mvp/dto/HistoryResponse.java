package com.lunchpick.mvp.dto;

import java.time.LocalDate;

public class HistoryResponse {

    private Long teamId;
    private Long restaurantId;
    private String restaurantName;
    private LocalDate date;

    public HistoryResponse() {
    }

    public HistoryResponse(Long teamId, Long restaurantId, String restaurantName, LocalDate date) {
        this.teamId = teamId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }
}
