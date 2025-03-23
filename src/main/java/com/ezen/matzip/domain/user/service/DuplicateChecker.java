package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.user.repository.BusinessRepository;
import com.ezen.matzip.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DuplicateChecker {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    public boolean isDuplicatedId(String id){
        return userRepository.existsByUserid(id) || businessRepository.existsById(id) || id.equals("admin");
    }
}


