package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.user.dto.LoginRequestDTO;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.service.Authservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private Authservice authservice;

    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute LoginRequestDTO loginRequestDTO, HttpSession session) {
        try{
            Object user = authservice.login(loginRequestDTO);

            String role;
            String redirectUrl;
            if (user instanceof User) {
                role = ((User) user).getRole().name();
                if ("ADMIN".equals(role)) {
                    redirectUrl = "/admin/main";
                } else {
                    redirectUrl = "/user/main";
                }
            } else if (user instanceof Business) {
                role = ((Business) user).getRole().name();
                redirectUrl = "/business/main";
            } else {
                throw new RuntimeException("알 수 없는 역할입니다.");
            }

            session.setAttribute("userid", loginRequestDTO.getId());
            session.setAttribute("role", role);

            return  ResponseEntity.status(HttpStatus.FOUND).header("Location", redirectUrl).build();

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}

//로그인,로그아웃
