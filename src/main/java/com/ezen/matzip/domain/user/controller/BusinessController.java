package com.ezen.matzip.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessController {

    @GetMapping("/business/main")
    public String businessMain() {
        return "business/main";
    }
}
