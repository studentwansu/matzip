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

//user/ → 회원 관리 (회원가입, 로그인, 회원정보 수정)

//Controller → 클라이언트 요청을 처리
//Service → 비즈니스 로직을 처리
//Repository → 데이터베이스 CRUD 처리
//DTO → 클라이언트와 데이터를 주고받을 때 사용하는 객체
//Entity → 실제 데이터베이스 테이블과 매칭되는 객체

//회원가입