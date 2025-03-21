package com.ezen.matzip.domain.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/main")
    public String adminMainPage() {
        return "관리자 메인 페이지입니다.";
    }
}
