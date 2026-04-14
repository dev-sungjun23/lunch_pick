package com.lunchpick.mvp.dto;

import jakarta.validation.constraints.NotNull;

public class VoteRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long restaurantId;

    public VoteRequest() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
