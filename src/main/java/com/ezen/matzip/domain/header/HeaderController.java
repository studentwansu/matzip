package com.ezen.matzip.domain.header;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
//@Controller
@RequestMapping
@RequiredArgsConstructor
public class HeaderController {

    private final HeaderService headerService;

    @ModelAttribute
//    @GetMapping("/admin/header")
    public void getAllInfo(Model model)
    {
        int activeRestaurants = headerService.getRestaurantActiveNum();
        int disabledRestaurants = headerService.getRestaurantDisabledNum();

        int activeUsers = headerService.getActiveUsers();
        int disabledUsers = headerService.getDisabledUsers();

        int reportedReviews = headerService.getReportedReviews();
        int unansweredQNAs = headerService.getUnansweredQNAs();

        model.addAttribute("activeRestaurants", activeRestaurants);
        model.addAttribute("disabledRestaurants", disabledRestaurants);
        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("disabledUsers", disabledUsers);
        model.addAttribute("reportedReviews", reportedReviews);
        model.addAttribute("unansweredQNAs", unansweredQNAs);

        System.out.println("activeRestaurant: " + activeRestaurants);
    }
}
