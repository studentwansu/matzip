package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.user.dto.BusinessRequestDTO;
import com.ezen.matzip.domain.user.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/businesses")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping("/register")
    public String register(@ModelAttribute BusinessRequestDTO businessRequestDTO) {
        businessService.registerBusiness(businessRequestDTO);
        return "사업자 회원 가입 성공!";
    }

    @GetMapping("/main")
    public String businessMainPage() {
        return "사업자 메인 페이지입니다.";
    }
}
