package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.Role;
import com.ezen.matzip.domain.user.dto.LoginRequestDTO;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.BusinessRepository;
import com.ezen.matzip.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Authservice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    public Object login(LoginRequestDTO loginRequest) {
        Optional<User> userOpt = userRepository.findByUserid(loginRequest.getId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            }
            return user;
        }

        Optional<Business> businessOpt = businessRepository.findByBusinessid(loginRequest.getId());
        if (businessOpt.isPresent()) {
            Business business = businessOpt.get();
            if (!business.getPassword().equals(loginRequest.getPassword())) {
                throw new RuntimeException("비밀번호가 일치하지 않습니다.");
            }
            return business;
        }

        if ("adminid".equals(loginRequest.getId()) && "adminpsd".equals(loginRequest.getPassword())) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setId("adminid");
            admin.setPassword("adminpsd");
            admin.setRole(Role.ADMIN);
            return admin;
        }

        throw new RuntimeException("존재하지 않는 사용자입니다.");
    }



}


