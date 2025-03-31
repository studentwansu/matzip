package com.ezen.matzip.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

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
}
