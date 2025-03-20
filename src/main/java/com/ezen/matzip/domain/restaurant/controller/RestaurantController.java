package com.ezen.matzip.domain.restaurant.controller;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{restaurantCode}")
        public String getRestaurantDetail(@PathVariable int restaurantCode, Model model) {
            Restaurant restaurant = restaurantService.getRestaurantDetail(restaurantCode);
            model.addAttribute("restaurant", restaurant);
            return "restaurant/restaurant";
        }
}