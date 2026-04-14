package com.lunchpick.mvp.dto;

public class RestaurantCandidateResponse {

    private Long id;
    private String name;
    private String category;
    private Integer distance;
    private Integer priceRange;
    private String address;
    private String placeUrl;

    public RestaurantCandidateResponse() {
    }

    public RestaurantCandidateResponse(Long id, String name, String category, Integer distance, Integer priceRange) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.priceRange = priceRange;
    }

    public RestaurantCandidateResponse(
        Long id,
        String name,
        String category,
        Integer distance,
        Integer priceRange,
        String address,
        String placeUrl
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.priceRange = priceRange;
        this.address = address;
        this.placeUrl = placeUrl;
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

    public String getAddress() {
        return address;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }
}
