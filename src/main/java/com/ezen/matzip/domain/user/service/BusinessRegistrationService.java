package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.Role;
import com.ezen.matzip.domain.user.dto.BusinessRequestDTO;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BusinessRegistrationService {

    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BusinessRegistrationService(BusinessRepository businessRepository, PasswordEncoder passwordEncoder) {
        this.businessRepository = businessRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Business register(BusinessRequestDTO dto) {
        if (businessRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        if (businessRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        if (businessRepository.existsByBusinessNumber(dto.getBusinessNumber())) {
            throw new RuntimeException("이미 등록된 사업자 번호입니다.");
        }
        if (businessRepository.existsByRestaurantName(dto.getRestaurantName())) {
            throw new RuntimeException("이미 존재하는 상호명입니다.");
        }
        if (businessRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("이미 존재하는 전화번호입니다.");
        }

        Business business = Business.builder()
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .passwordQuestion(dto.getPasswordQuestion())
                .passwordAnswer(dto.getPasswordAnswer())
                .businessNumber(dto.getBusinessNumber())
                .restaurantName(dto.getRestaurantName())
                .email(dto.getEmail())
                .role(Role.BUSINESS)
                .build();

        return businessRepository.save(business);
    }
}
