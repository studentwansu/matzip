package com.ezen.matzip.domain.restaurant.repository;

import com.ezen.matzip.domain.restaurant.entity.Menu;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.entity.RestaurantImage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Restaurant findByRestaurantCode(Integer restaurantCode);

    @Query("SELECT r FROM Restaurant r WHERE r.restaurantUniqueKeywords LIKE %:keyword% " +
            "OR r.restaurantDescription LIKE %:keyword% " +
            "OR r.restaurantName LIKE %:keyword% " +
            "OR r.restaurantService LIKE %:keyword% " +
            "OR r.restaurantLocation LIKE %:keyword%")
    List<Restaurant> findByRestaurantInfo(@Param("keyword") String keyword);

    @Query("SELECT r, " +
            "(CASE WHEN r.restaurantUniqueKeywords LIKE %:keyword% THEN 1 ELSE 0 END) + " +
            "(CASE WHEN r.restaurantDescription LIKE %:keyword% THEN 1 ELSE 0 END) + " +
            "(CASE WHEN r.restaurantName LIKE %:keyword% THEN 1 ELSE 0 END) + " +
            "(CASE WHEN r.restaurantService LIKE %:keyword% THEN 1 ELSE 0 END) + " +
            "(CASE WHEN r.restaurantLocation LIKE %:keyword% THEN 1 ELSE 0 END) AS score " +
            "FROM Restaurant r " +
            "WHERE r.restaurantUniqueKeywords LIKE CONCAT('%', :keyword, '%') " +
            "OR r.restaurantDescription LIKE CONCAT('%', :keyword, '%') " +
            "OR r.restaurantName LIKE CONCAT('%', :keyword, '%') " +
            "OR r.restaurantService LIKE CONCAT('%', :keyword, '%') " +
            "OR r.restaurantLocation LIKE CONCAT('%', :keyword, '%') " +
            "ORDER BY score DESC")
    List<Object[]> findRestaurantsByKeywordWithScore(@Param("keyword") String keyword);

    //완수 북마크 기능에 필요
    Restaurant findByRestaurantCode(int restaurantCode);

    Restaurant findByBusinessCode(int businessCode);


//    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Restaurant u WHERE u.businessCode = :businessCode")
//    Restaurant existsByBusinessCode(@Param("businessCode") int businessCode);
//
    // 희영 식당등록요청 목록조회에 필요
    // 상태 코드 기준으로 조회 (예: 대기중인 식당)
    List<Restaurant> findByRestaurantStatus(int restaurantStatus);

    int countAllByRestaurantActiveStatus(int activeStatus);

}
