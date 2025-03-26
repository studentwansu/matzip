package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.Role;
import com.ezen.matzip.domain.user.dto.UserRequestDTO;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional(rollbackOn = Exception.class)  // 모든 예외에 대해 롤백
    public User register(UserRequestDTO dto) {
        // 디버깅용 로그
        System.out.println("🚀 회원가입 시도: " + dto.getUserId());

        // 중복 검사
        if (userRepository.existsByUserId(dto.getUserId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        try {
            User user = User.builder()
                    .userId(dto.getUserId())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .phoneNumber(dto.getPhoneNumber())
                    .passwordQuestion(dto.getPasswordQuestion())
                    .passwordAnswer(dto.getPasswordAnswer())
                    .name(dto.getName())
                    .nationality(dto.getNationality())
                    .isVegan(dto.getIsVegan())
                    .categoryCode(dto.getCategoryCode())
                    .email(dto.getEmail())
                    .role(Role.USER)
                    .userReportCount(0)
                    .accountStatus(1)  // 활성 상태
                    .build();

            // DB 저장
            User savedUser = userRepository.save(user);
            System.out.println("✅ 회원가입 성공: " + savedUser.getUserId());
            return savedUser;

        } catch (Exception e) {
            System.err.println("❌ 회원가입 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("회원가입 중 오류 발생: " + e.getMessage());
        }
    }
}
