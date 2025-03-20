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
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m.restaurantCode FROM Menu m WHERE m.menuName LIKE %:menuName%")
    List<Restaurant> findRestaurantsByMenuName(@Param("menuName") String menuName);

    List<Menu> findByRestaurantCode(Restaurant restaurantCode);

}
