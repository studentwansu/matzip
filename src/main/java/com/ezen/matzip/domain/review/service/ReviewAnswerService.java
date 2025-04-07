package com.ezen.matzip.domain.review.service;

import com.ezen.matzip.domain.Role;
import com.ezen.matzip.domain.report.service.UserReportSyncService;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.dto.ReviewImageDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import com.ezen.matzip.domain.review.repository.ReviewAnswerRepository;
import com.ezen.matzip.domain.review.repository.ReviewImageRepository;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewAnswerService {

    private final ReviewAnswerRepository reviewAnswerRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final ReviewImageRepository reviewImageRepository;

    //완수 - 신고수 동기화에 필요
    private final UserRepository userRepository;
    private final UserReportSyncService userReportSyncService;

    public List<ReviewDTO> findReviewByBusinessCode(int businessCode) {
        List<Object[]> reviews = reviewAnswerRepository.findByBusinessCode(businessCode);

        List<ReviewDTO> result = new ArrayList<>();
        for (Object[] review : reviews) {
            Review e = (Review) review[0];
            Restaurant restaurant = (Restaurant) review[1];
            ReviewDTO dto = new ReviewDTO();
            dto.setUserCode(e.getUserCode());
            dto.setRestaurantName(restaurant);
            dto.setReviewCode(e.getReviewCode());
            dto.setReviewDate(e.getReviewDate());
            dto.setReviewContent(e.getReviewContent());
            dto.setReviewReply(e.getReviewReply());
            dto.setRating(e.getRating());

            List<ReviewImage> images = reviewImageRepository.findReviewImagesByReviewCode(e.getReviewCode());
            List<ReviewImageDTO> imageDTOs = images.stream()
                    .map(img -> modelMapper.map(img, ReviewImageDTO.class))
                    .collect(Collectors.toList());
            dto.setReviewImages(imageDTOs);

            result.add(dto);
        }
        return result;
    }

    public List<ReviewDTO> findReviewByBusinessCodeAndMonth(int businessCode, int month) {
        List<Object[]> reviews = reviewAnswerRepository.findByBusinessCodeAndReviewDate(businessCode, month);

        List<ReviewDTO> result = new ArrayList<>();
        for (Object[] review : reviews) {
            Review e = (Review) review[0];
            Restaurant restaurant = (Restaurant) review[1];
            ReviewDTO dto = new ReviewDTO();
            dto.setUserCode(e.getUserCode());
            dto.setRestaurantName(restaurant);
            dto.setReviewCode(e.getReviewCode());
            dto.setReviewDate(e.getReviewDate());
            dto.setReviewContent(e.getReviewContent());
            dto.setReviewReply(e.getReviewReply());
            dto.setRating(e.getRating());
            dto.setBusinessCode(e.getBusinessCode());
            result.add(dto);
        }
        return result;
    }

    public List<Review> getRecentReview(int businessCode) {
        return reviewAnswerRepository.findTop5ReviewByBusinessCodeOrderByReviewDateDesc(businessCode);
    }


    @Transactional
    public ReviewDTO modifyAnswer(ReviewDTO dto) {
        Review review = reviewAnswerRepository.findReviewByReviewCode(dto.getReviewCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰 없음"));
        System.out.println("리뷰리뷰리뷰" + review);

        System.out.println("수정 요청된 리뷰 코드: " + dto.getReviewCode());
        System.out.println("수정 요청된 비즈니스코드: " + dto.getBusinessCode());
        review.modifyAnswer(dto.getReviewReply());
        reviewAnswerRepository.save(review);

        ReviewDTO result = new ReviewDTO();
        result.setReviewCode(review.getReviewCode());
        result.setReviewDate(review.getReviewDate());
        result.setReviewContent(review.getReviewContent());
        result.setReviewReply(review.getReviewReply());
        result.setRating(review.getRating());
        result.setBusinessCode(review.getBusinessCode());
        result.setUserCode(review.getUserCode());

        return result;
    }


    @Transactional
    public void saveReply(int reviewCode, String replyContent){
        Review review = reviewAnswerRepository.findById(reviewCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));

        review.writeReply(replyContent);
        reviewAnswerRepository.save(review);
    }

    //완수-리뷰신고기능에 필요
    @Transactional
    public void increaseReportCount(int reviewCode) {
        Review review = reviewAnswerRepository.findById(reviewCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));
        review.incrementReportCount();
        reviewAnswerRepository.save(review);

        // 신고 대상이 일반 유저인 경우에만 동기화 (예: Role.USER)
        User user = userRepository.findById(review.getUserCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        if (user.getRole() == Role.USER) {  // Role 비교는 실제 구현에 맞게 수정
            userReportSyncService.updateUserReportCount(review.getUserCode());
        }
    }



}
