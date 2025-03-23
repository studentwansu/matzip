package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.user.dto.UserRequestDTO;
import com.ezen.matzip.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute UserRequestDTO userRequestDTO) {
        userService.registerUser(userRequestDTO);
        return "일반 회원 가입 성공!";
    }

    @GetMapping("/main")
    public String userMainPage() {
        return "일반 회원 메인 페이지입니다.";
    }




}
