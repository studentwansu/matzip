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
    @Transactional(rollbackOn = Exception.class)
    public User register(UserRequestDTO dto) {
        System.out.println("회원가입 시도: " + dto.getUserId());

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
                    .isVegan(dto.getIsVegan() ? 1 : 0)
                    .categoryCode(dto.getCategoryCode())
                    .email(dto.getEmail())
                    .role(Role.USER)
                    .userReportCount(0)
                    .accountStatus(0)
                    .build();

            User savedUser = userRepository.save(user);
            System.out.println("회원가입 성공: " + savedUser.getUserId());
            return savedUser;

        } catch (Exception e) {
            System.err.println("회원가입 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("회원가입 중 오류 발생: " + e.getMessage());
        }
    }
}
