package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.BusinessRepository;
import com.ezen.matzip.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserIdCheckService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    public UserIdCheckService(UserRepository userRepository, BusinessRepository businessRepository) {
        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
    }

    public boolean isUserIdAvailable(String userId) {
        // 로그로 확인
        boolean existsInUser = userRepository.existsByUserId(userId);
        boolean existsInBusiness = businessRepository.existsByUserId(userId);
        System.out.println("userId: " + userId + " - exists in User: " + existsInUser + ", exists in Business: " + existsInBusiness);

        return !existsInUser && !existsInBusiness;
    }

    public Integer getBusinessCodeByUserid(String userId) {
        Optional<Business> business = businessRepository.findByUserId(userId);
        return business.isPresent() ? business.get().getBusinessCode() : null;
    }
}
