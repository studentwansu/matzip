package com.ezen.matzip.domain.report.controller;

import com.ezen.matzip.domain.report.dto.ReportedUserDetailDTO;
import com.ezen.matzip.domain.report.service.ReportedUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ReportedUserDetailController {

    private final ReportedUserDetailService userDetailService;

    // GET: 신고된 유저 상세 페이지 (userId 기준으로 조회)
    @GetMapping("/reportedUserDetail")
    public String getReportedUserDetail(@RequestParam("userId") String userId, Model model) {
        ReportedUserDetailDTO userDetail = userDetailService.getUserDetail(userId);
        model.addAttribute("user", userDetail);
        return "domain/admin/admin_userreportdetail"; // Thymeleaf 템플릿 경로
    }

    // POST: 정지 처리/해제 요청 (userCode와 userId를 파라미터로 받음)
    @PostMapping("/updateUserStatus")
    public String updateUserStatus(@RequestParam("userCode") int userCode,
                                   @RequestParam("userId") String userId) {
        userDetailService.toggleUserBannedStatus(userCode);
        return "redirect:/admin/reportedUserDetail?userId=" + userId;
    }
}
