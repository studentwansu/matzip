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

    @GetMapping("/reportedUserDetail")
    public String getReportedUserDetail(@RequestParam("userId") String userId, Model model) {
        ReportedUserDetailDTO userDetail = userDetailService.getUserDetail(userId);
        model.addAttribute("user", userDetail);
        return "domain/admin/admin_userreportdetail";
    }

    @PostMapping("/updateUserStatus")
    public String updateUserStatus(@RequestParam("userCode") int userCode,
                                   @RequestParam("userId") String userId) {
        userDetailService.toggleUserBannedStatus(userCode);
        return "redirect:/admin/reportedUserDetail?userId=" + userId;
    }
}
