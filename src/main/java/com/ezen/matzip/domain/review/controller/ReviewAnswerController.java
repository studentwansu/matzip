package com.ezen.matzip.domain.review.controller;

import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.repository.ReviewAnswerRepository;
import com.ezen.matzip.domain.review.service.ReviewAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ReviewAnswerController {

    private final ReviewAnswerService reviewAnswerService;
    private ReviewAnswerRepository reviewAnswerRepository;
    private ModelMapper modelMapper;

    @GetMapping(value = "/business/answer/{businessCode}")
    public String findReviewByBusinessCode(@PathVariable int businessCode,
                                           @RequestParam(required = false) Integer month,
                                           Model model) {

        List<ReviewDTO> reviews;

        if (month != null) {
            reviews = reviewAnswerService.findReviewByBusinessCodeAndMonth(businessCode, month);
            model.addAttribute("selectedMonth", month);
        } else {
            reviews = reviewAnswerService.findReviewByBusinessCode(businessCode);
        }
        model.addAttribute("reviews", reviews);

        return "domain/review/review_answer";
    }

}
