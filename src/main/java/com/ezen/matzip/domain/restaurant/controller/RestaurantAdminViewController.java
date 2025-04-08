package com.ezen.matzip.domain.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/restaurants")
public class RestaurantAdminViewController {

    @GetMapping("/apply-list")
    public String showRestaurantApplyList() {
        return "domain/admin/admin_restapplylist"; // templates/admin_restapplylist.html
    }
}

