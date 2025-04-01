package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BusinessUpdateService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Business findByUserId(String userId) {
        return businessRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Business user not found"));
    }
}
