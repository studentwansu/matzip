package com.ezen.matzip.domain.report.service;

import com.ezen.matzip.domain.review.repository.ReviewRepository;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReportSyncService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // 특정 유저의 신고 수를 동기화하는 메서드 (리뷰 신고 수의 합계 반영)
    @Transactional
    public void updateUserReportCount(int userCode) {
        int totalReportCount = reviewRepository.sumReportCountByUserCode(userCode);
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found with code: " + userCode));
        user.setUserReportCount(totalReportCount);
        userRepository.save(user);
    }

    // 예: 모든 유저에 대해 주기적으로 동기화 (매 시간 0분마다 실행)
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void syncAllUserReportCounts() {
        userRepository.findAll().forEach(user -> {
            int totalReportCount = reviewRepository.sumReportCountByUserCode(user.getUserCode());
            user.setUserReportCount(totalReportCount);
            userRepository.save(user);
        });
    }
}
