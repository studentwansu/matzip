package com.ezen.matzip.domain.review.controller;

import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/{userCode}")
    public String findReviewByUserCode(@PathVariable int userCode, Model model) {

        List<ReviewDTO> resultReview = reviewService.findReviewByUserCode(userCode);

        model.addAttribute("review", resultReview);

        return "review/review_list";
    }

}

//review/ → 리뷰 시스템 (리뷰 작성, 수정, 삭제, 조회)

//Controller → 클라이언트 요청을 처리
//Service → 비즈니스 로직을 처리
//Repository → 데이터베이스 CRUD 처리
//DTO → 클라이언트와 데이터를 주고받을 때 사용하는 객체
//Entity → 실제 데이터베이스 테이블과 매칭되는 객체
