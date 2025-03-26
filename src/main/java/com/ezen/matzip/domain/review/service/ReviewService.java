package com.ezen.matzip.domain.review.service;

import com.ezen.matzip.domain.reservation.dto.ReservationDTO;
import com.ezen.matzip.domain.reservation.entity.Reservation;
import com.ezen.matzip.domain.reservation.repository.ReservationRepository;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.dto.ReviewImageDTO;
import com.ezen.matzip.domain.review.entity.Review;
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
        System.out.println("test231231:" + reviewDTO.getReviewCode());
        Review foundReview = reviewRepository.findByReviewCode(reviewDTO.getReviewCode());
//        Review foundReview = reviewRepository.findByReviewCode(reviewDTO.getReviewCode());

//        reviewRepository.updateReviewByReviewCode(dto.getReviewCode(), );
        foundReview.modifyReview(reviewDTO.getReviewContent());
    }


    public List<ReviewDTO> findReviewByReviewCode(int userCode) {

        List<ReviewDTO> reviews = findReviewByUserCode(userCode);
        for (ReviewDTO dto : reviews) {
            List<ReviewImage> imgs = reviewImageRepository.findByReviewCode(dto.getReviewCode());
            List<ReviewImageDTO> imgDto = new ArrayList<>();
            for (ReviewImage img : imgs) {
                ReviewImageDTO imgDTO = modelMapper.map(img, ReviewImageDTO.class);
                imgDto.add(imgDTO);
            }
            dto.setReviewImages(imgDto);
        }
        return reviews;
    }


    public List<ReservationDTO> findReservationByUserCode(int userCode){
        List<Reservation> reservations = reservationRepository.findReservationByUserCode(userCode);

        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class)).toList();
    }

    @Transactional
    public void writeReview(ReviewDTO reviewDTO) {
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



        reviewRepository.save(review);
    }

//    public List<ReservationDTO> findReservationByUserCode(int userCode) {
//        List<ReservationDTO> reservations = findReservationByUserCode(userCode);
//    }
}
