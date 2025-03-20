package com.ezen.matzip.domain.restaurant.service;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant getRestaurantDetail(int restaurantCode) {
        return restaurantRepository.findByRestaurantCode(restaurantCode)
                .orElseThrow(IllegalArgumentException::new);
    }
}

