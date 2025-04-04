package com.ezen.matzip.domain.restaurant.repository;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.entity.RestaurantImage;
import com.ezen.matzip.domain.restaurant.entity.RestaurantKeyword;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantImageRepository extends JpaRepository <RestaurantImage, Integer> {

    @Query("SELECT i FROM RestaurantImage i WHERE i.restaurantCode.restaurantCode = :restaurantCode")
    List<RestaurantImage> findRestaurantImageByRestaurantCode(@Param("restaurantCode") int restaurantCode);

    List<RestaurantImage> findRestaurantImageByRestaurantCode(Restaurant restaurantCode);
}
