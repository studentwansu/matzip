package com.ezen.matzip.domain.review.controller;

import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import com.ezen.matzip.domain.review.repository.ReviewAnswerRepository;
import com.ezen.matzip.domain.review.repository.ReviewImageRepository;
import com.ezen.matzip.domain.review.service.ReviewAnswerService;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.BusinessRepository;
import com.ezen.matzip.domain.user.service.BusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
//
//@Slf4j
//@Controller
//@RequestMapping
//@RequiredArgsConstructor
//public class ReviewAnswerController {
//
//    private final ReviewAnswerService reviewAnswerService;
//    private final ReviewImageRepository reviewImageRepository;
//    private ReviewAnswerRepository reviewAnswerRepository;
//    private ModelMapper modelMapper;
//    private final BusinessRepository businessRepository;
//
//    @GetMapping(value = "/business/answer/{businessCode}")
//    public String findReviewByBusinessCode(@PathVariable int businessCode,
//                                           @RequestParam(required = false) Integer month,
//                                           Model model) {
//
//        List<ReviewDTO> reviews;
//
//        if (month != null) {
//            reviews = reviewAnswerService.findReviewByBusinessCodeAndMonth(businessCode, month);
//            model.addAttribute("selectedMonth", month);
//        } else {
//            reviews = reviewAnswerService.findReviewByBusinessCode(businessCode);
//        }
//        model.addAttribute("reviews", reviews);
//
//        return "domain/review/review_answer";
//    }
//
//
//
//    @GetMapping("/business/answer/write/{reviewCode}")
//    public String writeAnswer(@PathVariable int reviewCode, Model model) {
//        Optional<Review> review = reviewAnswerRepository.findById(reviewCode);
//        List<ReviewImage> reviewImages = reviewImageRepository.findReviewImagesByReviewCode(reviewCode);
//        model.addAttribute("review", review);
//        model.addAttribute("reviewImages", reviewImages);
//        return "domain/review/review_answer";
//    }
//
//    @PostMapping("/business/answer/modify")
//    public String modifyAnswer(@ModelAttribute ReviewDTO reviewDTO) {
//        reviewDTO = reviewAnswerService.modifyAnswer(reviewDTO);
//        return "redirect:/business/answer/" + reviewDTO.getBusinessCode();
//    }
//
//    @PostMapping("/business/answer/save/{reviewCode}")
//    public String saveAnswer(@ModelAttribute ReviewDTO reviewDTO, @RequestParam int reviewCode, @RequestParam String replyContent){
//        reviewAnswerService.saveReply(reviewCode, replyContent);
//        return "redirect:/business/answer/" + reviewDTO.getBusinessCode();
//    }
//
//    @PostMapping("/business/answer/delete/{reviewCode}")
//    public String deleteAnswer(@ModelAttribute ReviewDTO reviewDTO, @PathVariable int reviewCode) {
//        reviewAnswerService.saveReply(reviewCode, null); // null로 설정 = 삭제
//        return "redirect:/business/answer/" + reviewDTO.getBusinessCode();
//    }
//
//
//}



@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ReviewAnswerController {

    private final ReviewAnswerService reviewAnswerService;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewAnswerRepository reviewAnswerRepository;
    private final ModelMapper modelMapper;
    private final BusinessService businessService;

    // 리뷰 목록 조회
    @GetMapping("/business/answer")
    public String findReviewByPrincipal(@RequestParam(required = false) Integer month,
                                        Principal principal,
                                        Model model) {
        Business business = businessService.findByUserId(principal.getName());
        int businessCode = business.getBusinessCode();

        List<ReviewDTO> reviews;
        if (month != null) {
            reviews = reviewAnswerService.findReviewByBusinessCodeAndMonth(businessCode, month);
            model.addAttribute("selectedMonth", month);
        } else {
            reviews = reviewAnswerService.findReviewByBusinessCode(businessCode);
        }

        model.addAttribute("reviews", reviews);
        model.addAttribute("businessCode", businessCode);
        return "domain/review/review_answer";
    }

    // 리뷰 상세 보기 (답변 작성용)
    @GetMapping("/business/answer/write/{reviewCode}")
    public String writeAnswer(@PathVariable int reviewCode, Model model) {
        Optional<Review> review = reviewAnswerRepository.findById(reviewCode);
        List<ReviewImage> reviewImages = reviewImageRepository.findReviewImagesByReviewCode(reviewCode);
        model.addAttribute("review", review);
        model.addAttribute("reviewImages", reviewImages);
        return "domain/review/review_answer";
    }

    // 답변 수정
    @PostMapping("/business/answer/modify")
    public String modifyAnswer(@ModelAttribute ReviewDTO reviewDTO, Principal principal) {
        Business business = businessService.findByUserId(principal.getName());
        int businessCode = business.getBusinessCode();
        reviewDTO.setBusinessCode(businessCode);

        reviewAnswerService.modifyAnswer(reviewDTO);
        return "redirect:/business/answer";
    }

    // 답변 저장
    @PostMapping("/business/answer/save/{reviewCode}")
    public String saveAnswer(@ModelAttribute ReviewDTO reviewDTO,
                             @PathVariable int reviewCode,
                             @RequestParam String replyContent,
                             Principal principal) {
        Business business = businessService.findByUserId(principal.getName());
        int businessCode = business.getBusinessCode();
        reviewDTO.setBusinessCode(businessCode);

        reviewAnswerService.saveReply(reviewCode, replyContent);
        return "redirect:/business/answer";
    }

    // 답변 삭제
    @PostMapping("/business/answer/delete/{reviewCode}")
    public String deleteAnswer(@PathVariable int reviewCode) {
        reviewAnswerService.saveReply(reviewCode, null); // null로 설정 = 삭제
        return "redirect:/business/answer";
    }


    //완수-신고기능에 필요
    @PostMapping("/business/review/report")
    @ResponseBody
    public ResponseEntity<?> reportReview(@RequestParam int reviewCode) {
        reviewAnswerService.increaseReportCount(reviewCode);
        return ResponseEntity.ok("신고 처리 완료");
    }
}


