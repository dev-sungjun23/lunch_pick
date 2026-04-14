package com.lunchpick.mvp.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByDistanceLessThanEqualAndPriceRangeLessThanEqual(Integer distance, Integer priceRange);
}