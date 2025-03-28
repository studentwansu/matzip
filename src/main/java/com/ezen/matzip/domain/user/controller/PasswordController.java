package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.user.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class PasswordController {

    private final PasswordResetService passwordResetService;

    @Autowired
    public PasswordController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/findpwd")
    public String findPwd() {
        return "common/findpwd";
    }

    @PostMapping("checkUserInfo")
    @RequestBody
    public ResponseEntity<Map<String, Object>> checkUserInfo(
            @RequestParam String userId,
            @
    )
}
