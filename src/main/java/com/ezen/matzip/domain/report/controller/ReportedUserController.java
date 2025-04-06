package com.ezen.matzip.domain.report.controller;

import com.ezen.matzip.domain.report.dto.ReportedUserDTO;
import com.ezen.matzip.domain.report.dto.SearchCriteria;
import com.ezen.matzip.domain.report.service.ReportedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ReportedUserController {

    private final ReportedUserService reportedUserService;

    @GetMapping("/reportedUsers")
    public String getReportedUsers(
            @RequestParam(required = false, defaultValue = "1") int reportCountThreshold,
            @RequestParam(required = false, defaultValue = "false") boolean unprocessed,
            @RequestParam(required = false, defaultValue = "false") boolean processed,
            @RequestParam(required = false, defaultValue = "") String userId,
            @RequestParam(required = false, defaultValue = "1") int page,
            Model model
    ) {
        SearchCriteria criteria = new SearchCriteria(reportCountThreshold, unprocessed, processed, userId);
        Page<ReportedUserDTO> userPage = reportedUserService.getReportedUsers(criteria, page);

        int unprocessedCount = reportedUserService.countByAccountStatus(0);
        int processedCount = reportedUserService.countByAccountStatus(1);
        // 최대 신고 횟수 (조회된 유저 중 가장 높은 신고 수, 없으면 1)
        int maxReportCount = userPage.getContent().stream()
                .mapToInt(ReportedUserDTO::getUserReportCount)
                .max().orElse(1);

        model.addAttribute("reportedUsers", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("unprocessedCount", unprocessedCount);
        model.addAttribute("processedCount", processedCount);
        model.addAttribute("searchCriteria", criteria);
        model.addAttribute("maxReportCount", maxReportCount);

        return "domain/admin/admin_userreportlist"; // 템플릿 경로에 맞게 수정
    }
}
