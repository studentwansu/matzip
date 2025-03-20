package com.ezen.matzip.domain.restaurant.repository;

import com.ezen.matzip.domain.restaurant.entity.Menu;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.entity.RestaurantStarKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantStarKeywordRepository extends JpaRepository<RestaurantStarKeyword, Integer> {

    List<RestaurantStarKeyword> findByRestaurantCode(Restaurant restaurantCode);

    @Query("SELECT m.restaurantCode FROM Menu m WHERE m.menuName LIKE %:keyword%")
    List<Restaurant> findRestaurantsByRestaurantKeyword(@Param("keyword") String keyword);


}
