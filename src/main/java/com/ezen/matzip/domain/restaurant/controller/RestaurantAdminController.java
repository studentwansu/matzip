package com.ezen.matzip.domain.restaurant.controller;

import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.enums.RestaurantStatus;
import com.ezen.matzip.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/restaurants")
@RequiredArgsConstructor
public class RestaurantAdminController {

    private final RestaurantService restaurantService;

    // JSON API 메서드에는 @ResponseBody
    @ResponseBody
    @PatchMapping("/{restaurantCode}/approve")
    public ResponseEntity<Void> approveRestaurant(@PathVariable int restaurantCode) {
        restaurantService.updateRestaurantStatus(restaurantCode, RestaurantStatus.APPROVED.getCode());
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PatchMapping("/{restaurantCode}/reject")
    public ResponseEntity<Void> rejectRestaurant(@PathVariable int restaurantCode) {
        restaurantService.updateRestaurantStatus(restaurantCode, RestaurantStatus.REJECTED.getCode());
        return ResponseEntity.ok().build();
    }

    // 뷰 반환 메서드는 @ResponseBody 가 없으니, 뷰 리졸버가 동작합니다.
    @GetMapping("/apply-list")
    public String showRestaurantApplyList() {
        return "domain/admin/admin_restapplylist";
        // → templates/domain/admin/admin_restapplylist.html
    }
    @ResponseBody
    @GetMapping("/pending")
    public ResponseEntity<List<RestaurantDTO>> getPendingRestaurants() {
        return ResponseEntity.ok(restaurantService.getPendingRestaurants());
    }

}


