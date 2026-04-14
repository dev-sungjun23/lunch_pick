package com.lunchpick.mvp.restaurant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantSeeder implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;

    public RestaurantSeeder(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void run(String... args) {
        if (restaurantRepository.count() > 0) {
            return;
        }

        List<Restaurant> restaurants = List.of(
            new Restaurant("김치찌개집", "한식", 8, 9000),
            new Restaurant("비빔밥하우스", "한식", 10, 10000),
            new Restaurant("초밥마켓", "일식", 12, 13000),
            new Restaurant("라멘공방", "일식", 9, 11000),
            new Restaurant("짜장면관", "중식", 7, 8000),
            new Restaurant("마라탕전문점", "중식", 11, 12000),
            new Restaurant("파스타키친", "양식", 10, 14000),
            new Restaurant("샐러드카페", "샐러드", 6, 9500)
        );

        restaurantRepository.saveAll(restaurants);
    }
}
