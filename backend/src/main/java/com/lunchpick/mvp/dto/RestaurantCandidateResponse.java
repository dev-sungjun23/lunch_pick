package com.lunchpick.mvp.dto;

public class RestaurantCandidateResponse {

    private Long id;
    private String name;
    private String category;
    private Integer distance;
    private Integer priceRange;

    public RestaurantCandidateResponse() {
    }

    public RestaurantCandidateResponse(Long id, String name, String category, Integer distance, Integer priceRange) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.priceRange = priceRange;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getPriceRange() {
        return priceRange;
    }
}
