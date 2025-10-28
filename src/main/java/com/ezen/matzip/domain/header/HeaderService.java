package com.ezen.matzip.domain.header;

import com.ezen.matzip.domain.board.qna.repository.QnaRepository;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import com.ezen.matzip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeaderService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final QnaRepository qnaRepository;

    public int getRestaurantActiveNum()
    {
        return restaurantRepository.countAllByRestaurantActiveStatus(1);
    }

    public int getRestaurantDisabledNum() { return restaurantRepository.countAllByRestaurantActiveStatus(0); }

    public int getUserNum()
    {
        return userRepository.countAllBy();
    }

    public int getActiveUsers()
    {
        return userRepository.countByAccountStatus(0);
    }

    public int getDisabledUsers()
    {
        return userRepository.countByAccountStatus(1);
    }

    public int getReportedReviews()
    {
        return reviewRepository.countByReviewReportCountGreaterThan(0) - reviewRepository.countByHiddenFlag(1);
    }

    public int getUnansweredQNAs()
    {
        return qnaRepository.countByAnswerIsNull();
    }
}
