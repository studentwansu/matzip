package com.ezen.matzip.domain.report.service;

import com.ezen.matzip.domain.report.dto.ReportedUserDetailDTO;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportedUserDetailService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReportedUserDetailDTO getUserDetail(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        List<Object[]> reviewObjects = reviewRepository.findByUserCode(user.getUserCode());

        List<ReviewDTO> reviewDTOList = reviewObjects.stream()
                .map(obj -> (Review) obj[0])
                .filter(review -> review.getReviewReportCount() >= 1)
                .map(review -> {
                    ReviewDTO dto = new ReviewDTO();
                    dto.setReviewCode(review.getReviewCode());
                    dto.setReviewContent(review.getReviewContent());
                    dto.setReviewDate(review.getReviewDate());
                    dto.setReviewReportCount(review.getReviewReportCount());
                    dto.setHiddenFlag(review.getHiddenFlag());
                    dto.setUserCode(review.getUserCode());
                    return dto;
                }).collect(Collectors.toList());

        boolean banned = (user.getAccountStatus() == 1);
        return new ReportedUserDetailDTO(user.getUserCode(), user.getUserId(), user.getUserReportCount(), banned, reviewDTOList);
    }

    @Transactional
    public void toggleUserBannedStatus(int userCode) {
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found with code: " + userCode));
        int newStatus = user.getAccountStatus() == 0 ? 1 : 0;
        user.setAccountStatus(newStatus);
        userRepository.save(user);

        int newHiddenFlag = (newStatus == 1) ? 1 : 0;
        reviewRepository.updateHiddenFlagByUserCode(userCode, newHiddenFlag);
    }
}
