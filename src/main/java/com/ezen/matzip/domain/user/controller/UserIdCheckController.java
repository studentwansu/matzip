package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.user.service.UserIdCheckService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserIdCheckController {

    private final UserIdCheckService userIdCheckService;

    public UserIdCheckController(UserIdCheckService userIdCheckService) {
        this.userIdCheckService = userIdCheckService;
    }

//    @PostMapping("/checkUserId")
//    @ResponseBody
//    public Map<String, Object> checkUserId(@RequestParam("userId") String userId) {
//        boolean available = userIdCheckService.isUserIdAvailable(userId);
//        Map<String, Object> response = new HashMap<>();
//        return response;
//    }

    @PostMapping("/checkUserId")
    @ResponseBody
    public Map<String, Object> checkUserId(@RequestParam("userId") String userId) {
        userId = userId.trim(); // 서버측에서도 trim
        boolean available = userIdCheckService.isUserIdAvailable(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("available", available);
        return response;
    }
}
