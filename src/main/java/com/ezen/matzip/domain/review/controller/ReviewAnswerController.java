package com.ezen.matzip.domain.review.controller;

import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.repository.ReviewAnswerRepository;
import com.ezen.matzip.domain.review.service.ReviewAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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



    @GetMapping("/business/answer/write/{reviewCode}")
    public String writeAnswer(@PathVariable int reviewCode, Model model) {
        Optional<Review> review = reviewAnswerRepository.findById(reviewCode);
        model.addAttribute("review", review);
        return "domain/review/review_answer";
    }

    @PostMapping("/business/answer/modify")
    public String modifyAnswer(@ModelAttribute ReviewDTO reviewDTO) {
        reviewAnswerService.modifyAnswer(reviewDTO);
        return "redirect:/business/answer/" + reviewDTO.getBusinessCode();
    }

    @PostMapping("/business/answer/save/{reviewCode}")
    public String saveAnswer(@ModelAttribute ReviewDTO reviewDTO, @RequestParam int reviewCode, @RequestParam String replyContent){
        reviewAnswerService.saveReply(reviewCode, replyContent);
        return "redirect:/business/answer/" + reviewDTO.getBusinessCode();
    }

    @PostMapping("/business/answer/delete/{reviewCode}")
    public String deleteAnswer(@ModelAttribute ReviewDTO reviewDTO, @PathVariable int reviewCode) {
        reviewAnswerService.saveReply(reviewCode, null); // null로 설정 = 삭제
        return "redirect:/business/answer/" + reviewDTO.getBusinessCode();
    }


}
