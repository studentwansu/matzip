package com.ezen.matzip.domain.restaurant.repository;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findByRestaurantCode(int restaurantCode);
}
