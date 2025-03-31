package com.ezen.matzip.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/main")
    public String userMain() {
        return "main/main";
    }

    @GetMapping("/")
    public String guestMain() {
        return "main/main";
    }

    @GetMapping("/login")
    public String login() {
        return "common/login";
    }

//    @GetMapping("/user/myinfo")
//    public String userMyinfo() {
//        return "domain/sign/user_myinfo";
//    }
}
