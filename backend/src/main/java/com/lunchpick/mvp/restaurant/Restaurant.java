package com.lunchpick.mvp.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private Integer distance;
    private Integer priceRange;

    protected Restaurant() {
    }

    public Restaurant(String name, String category, Integer distance, Integer priceRange) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setPriceRange(Integer priceRange) {
        this.priceRange = priceRange;
    }

}