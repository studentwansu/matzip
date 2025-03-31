package com.ezen.matzip.domain.review.service;

import com.ezen.matzip.domain.reservation.dto.ReservationDTO;
import com.ezen.matzip.domain.reservation.entity.Reservation;
import com.ezen.matzip.domain.reservation.repository.ReservationRepository;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.dto.ReviewImageDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.repository.RestaurantReviewRepository;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import com.ezen.matzip.domain.review.repository.ReviewImageRepository;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public List<ReviewDTO> findReviewByUserCode(int userCode) {

        List<Object[]> reviewList = reviewRepository.findByUserCode(userCode);
        List<ReviewDTO> result = new ArrayList<>();
        for (Object[] review : reviewList) {
            Review e = (Review) review[0];
            Restaurant restaurant = (Restaurant) review[1];
            ReviewDTO dto = new ReviewDTO();
            dto.setUserCode(e.getUserCode());
            dto.setRestaurantName(restaurant);
            dto.setReviewCode(e.getReviewCode());
            dto.setReviewDate(e.getReviewDate());
            dto.setReviewContent(e.getReviewContent());
            dto.setRating(e.getRating());

            result.add(dto);
        }
            return result;
    }


    @Transactional
    public void deleteReview(int reviewCode) {
        reviewRepository.deleteById(reviewCode);
    }


    @Transactional
    public void modifyReview(ReviewDTO reviewDTO) {
        System.out.println("수정 요청 받은 리뷰 코드: " + reviewDTO.getReviewCode());
        System.out.println("수정 요청 받은 평점: " + reviewDTO.getRating());
//        Optional<Review> optionalReview = reviewRepository.findByReviewCode(reviewDTO.getReviewCode());
        List<Object> reviews = findReviewAndReviewImagesByReviewCode(reviewDTO.getReviewCode());
        Review foundReview = (Review) reviews.get(0);
        List<ReviewImage> reviewImages = new ArrayList<>();
        for (int i = 1; i < reviews.size(); i++) {
            ReviewImage reviewImage = (ReviewImage) reviews.get(i); // 실제 객체
            ReviewImageDTO reviewImageDTO = modelMapper.map(reviewImage, ReviewImageDTO.class); // DTO로 변환
            reviewImageRepository.save(reviewImage);
            reviewImages.add(reviewImage);
        }


//        for (int i = 1; i < reviews.size(); i++) {
//            ReviewImageDTO reviewImageDTO = (ReviewImageDTO) reviews.get(i);
//            ReviewImage reviewImage = new ReviewImage(foundReview, reviewImageDTO.getReviewImagePath(), reviewImageDTO.getReviewOriginalName(), reviewImageDTO.getReviewSaveName());
//            reviewImageRepository.save(reviewImage);
//            reviewImages.add(reviewImage);
//
//        }
        // 예외를 명시적으로 처리
//        Review foundReview = review.get(0).orElseThrow(() ->
//                new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다. 리뷰 코드: " + reviewDTO.getReviewCode())
//        );

        // 리뷰 수정

        foundReview.modifyReview(reviewDTO.getReviewContent(), reviewDTO.getRating());
        reviewRepository.save(foundReview);

    }


//    public List<ReviewDTO> findReviewByReviewCode(int userCode) {

//        List<ReviewDTO> reviews = findReviewByUserCode(userCode);
//        for (ReviewDTO dto : reviews) {
//            List<ReviewImage> imgs = reviewImageRepository.findReviewImagesByReviewCode(dto.getReviewCode());
//            List<ReviewImageDTO> imgDto = new ArrayList<>();
//            for (ReviewImage img : imgs) {
//                ReviewImageDTO imgDTO = modelMapper.map(img, ReviewImageDTO.class);
//                ReviewImageDTO imgDTO = new ReviewImageDTO()
//                imgDto.add(imgDTO);
//            }
//            dto.setReviewImages(imgDto);
//        }
//        return reviews;
//    }

//    public List<Object> findReviewAndReviewImagesByReviewCode(int reviewCode) {
//        List<Object> result = new ArrayList<>();
//        Optional<Review> review = reviewRepository.findByReviewCode(reviewCode);
//        List<ReviewImage> reviewImages = reviewImageRepository.findReviewImagesByReviewCode(reviewCode);
//        result.add(review);
//        for (ReviewImage reviewImage : reviewImages) {
//            result.add(reviewImage);
//        }
//
//        return result;
//    }

    public List<Object> findReviewAndReviewImagesByReviewCode(int reviewCode) {
        List<Object> result = new ArrayList<>();

        // Optional 처리
        Review review = reviewRepository.findByReviewCode(reviewCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다. 리뷰 코드: " + reviewCode));
        result.add(review);

        List<ReviewImage> reviewImages = reviewImageRepository.findReviewImagesByReviewCode(reviewCode);
        result.addAll(reviewImages);

        return result;
    }


    public List<ReservationDTO> findReservationByUserCode(int userCode){
        List<Reservation> reservations = reservationRepository.findReservationByUserCode(userCode);

        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class)).toList();
    }

    @Transactional
    public void writeReview(ReviewDTO reviewDTO, List<ReviewImageDTO> reviewImageDTO) {
        // 레스토랑 조회
        Restaurant restaurant = restaurantRepository.findById(reviewDTO.getRestaurantCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 레스토랑을 찾을 수 없습니다."));

        // 리뷰 엔티티 생성
        Review review = Review.builder()
                .reviewContent(reviewDTO.getReviewContent())
                .rating(reviewDTO.getRating())
                .userCode(reviewDTO.getUserCode())
                .reservationCode(reviewDTO.getReservationCode())
                .restaurantCode(restaurant)  // 연관관계 설정
                .businessCode(restaurant.getBusinessCode())
                .build();
        review.writeReview();
        reviewRepository.save(review);
//        List<ReviewImage> reviewImages = new ArrayList<>();
        for (ReviewImageDTO dto : reviewImageDTO) {
            ReviewImage reviewImage = new ReviewImage(
                    review, dto.getReviewImagePath(), dto.getReviewOriginalName(), dto.getReviewSaveName());
            reviewImageRepository.save(reviewImage);
        }

    }

//    public List<ReservationDTO> findReservationByUserCode(int userCode) {
//        List<ReservationDTO> reservations = findReservationByUserCode(userCode);
//    }
}
