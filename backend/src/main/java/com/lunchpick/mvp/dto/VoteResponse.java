package com.lunchpick.mvp.dto;

public class VoteResponse {

    private Long memberId;
    private Long restaurantId;

    public VoteResponse() {
    }

    public VoteResponse(Long memberId, Long restaurantId) {
        this.memberId = memberId;
        this.restaurantId = restaurantId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }
}
