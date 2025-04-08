package com.ezen.matzip.domain.restaurant.controller;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.enums.RestaurantStatus;
import com.ezen.matzip.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/restaurants")
@RequiredArgsConstructor
public class RestaurantAdminController {

    private final RestaurantService restaurantService;

    // 승인 처리
    @PatchMapping("/{restaurantCode}/approve")
    public ResponseEntity<Void> approveRestaurant(@PathVariable int restaurantCode) {
        restaurantService.updateRestaurantStatus(restaurantCode, RestaurantStatus.APPROVED.getCode());
        return ResponseEntity.ok().build();
    }

    // 반려 처리
    @PatchMapping("/{restaurantCode}/reject")
    public ResponseEntity<Void> rejectRestaurant(@PathVariable int restaurantCode) {
        restaurantService.updateRestaurantStatus(restaurantCode, RestaurantStatus.REJECTED.getCode());
        return ResponseEntity.ok().build();
    }



}

