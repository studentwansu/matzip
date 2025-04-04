package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.service.ReviewAnswerService;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.service.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BusinessController {
    private final ReviewAnswerService reviewAnswerService;

    public BusinessController(ReviewAnswerService reviewAnswerService) {
        this.reviewAnswerService = reviewAnswerService;
    }

//    @GetMapping("/business/main")
//    public String businessMain() {
//        return "domain/store/store_main";
//    }

    @GetMapping("/business/main")
    public String businessMain(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            Business business = userDetails.getBusiness();  // 👈 로그인한 유저 객체

            List<Review> recentReviews = reviewAnswerService.getRecentReview(business.getBusinessCode());

            model.addAttribute("businessCode", business.getBusinessCode());  // 👈 main.html에서 사용 가능
            model.addAttribute("recentReviews", recentReviews);
        }
        return "domain/store/store_main";
    }


}
