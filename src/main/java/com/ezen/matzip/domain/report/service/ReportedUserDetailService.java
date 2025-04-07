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
        // userId를 기준으로 User 엔티티 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        // 팀원이 작성한 메서드를 이용해 Object[] 결과를 가져옴
        List<Object[]> reviewObjects = reviewRepository.findByUserCode(user.getUserCode());
        List<ReviewDTO> reviewDTOList = reviewObjects.stream().map(obj -> {
            // obj[0]는 Review 엔티티, obj[1]는 r.restaurantCode (int 혹은 해당 값)
            Review review = (Review) obj[0];
            // restaurantCode는 이미 review 객체에 포함되어 있거나 별도로 활용 가능
            ReviewDTO dto = new ReviewDTO();
            dto.setReviewCode(review.getReviewCode());
            dto.setReviewContent(review.getReviewContent());
            dto.setReviewDate(review.getReviewDate());
            dto.setReviewReportCount(review.getReviewReportCount());
            dto.setHiddenFlag(review.getHiddenFlag());
            dto.setUserCode(review.getUserCode());
            // 추가 필드 매핑 (필요하다면)
            return dto;
        }).collect(Collectors.toList());

        // accountStatus: 1이면 정지 상태, 0이면 활성 상태
        boolean banned = (user.getAccountStatus() == 1);
        return new ReportedUserDetailDTO(user.getUserCode(), user.getUserId(), user.getUserReportCount(), banned, reviewDTOList);
    }

    @Transactional
    public void toggleUserBannedStatus(int userCode) {
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found with code: " + userCode));
        // 계정 상태 토글: 0(활성) -> 1(정지), 1(정지) -> 0(활성)
        int newStatus = user.getAccountStatus() == 0 ? 1 : 0;
        user.setAccountStatus(newStatus);
        userRepository.save(user);

        // 계정 정지 시 모든 리뷰를 숨김 처리(1), 계정 활성화 시 리뷰를 노출(0)
        int newHiddenFlag = (newStatus == 1) ? 1 : 0;
        reviewRepository.updateHiddenFlagByUserCode(userCode, newHiddenFlag);
    }
}
