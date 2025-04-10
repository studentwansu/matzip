package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.service.ReviewAnswerService;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BusinessController {
    private final ReviewAnswerService reviewAnswerService;

    // 민지 - 사업자 메인 페이지 부분 최근 리뷰 5개 가져오기
    @GetMapping("/business/main")
    public String businessMain(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {

            Business business = userDetails.getBusiness();  // 👈 로그인한 유저 객체

            List<Review> recentReviews = reviewAnswerService.getRecentReview(business.getBusinessCode());
            model.addAttribute("recentReviews", recentReviews);
        }
        return "domain/store/store_main";
    }


}
