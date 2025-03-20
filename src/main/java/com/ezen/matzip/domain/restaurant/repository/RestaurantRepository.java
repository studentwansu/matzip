package com.ezen.matzip.domain.restaurant.repository;

import com.ezen.matzip.domain.restaurant.entity.Menu;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r WHERE r.restaurantUniqueKeywords LIKE %:keyword% " +
            "OR r.restaurantDescription LIKE %:keyword% " +
            "OR r.restaurantName LIKE %:keyword% " +
            "OR r.restaurantService LIKE %:keyword% " +
            "OR r.restaurantLocation LIKE %:keyword%")
    List<Restaurant> findByRestaurantInfo(@Param("keyword") String keyword);


}
