package com.ezen.matzip.domain.report.controller;

import com.ezen.matzip.domain.report.dto.ReportedReviewDTO;
import com.ezen.matzip.domain.report.dto.SearchCriteria;
import com.ezen.matzip.domain.report.service.ReportService;
import com.ezen.matzip.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/admin_revreportlist")
    public String getReportedReviews(
            @RequestParam(required = false, defaultValue = "1") int reportCountThreshold,
            @RequestParam(required = false, defaultValue = "false") boolean unprocessed,
            @RequestParam(required = false, defaultValue = "false") boolean processed,
            @RequestParam(required = false, defaultValue = "") String userId,
            @RequestParam(required = false, defaultValue = "1") int page,
            Model model
    ) {
        SearchCriteria criteria = new SearchCriteria(reportCountThreshold, unprocessed, processed, userId);
        Page<ReportedReviewDTO> reviewPage = reportService.getReportedReviews(criteria, page);

        int unprocessedCount = reportService.countByHiddenFlag(0);
        int processedCount = reportService.countByHiddenFlag(1);

        // 신고 횟수 슬라이더 최대값 (현재 페이지 리뷰 중 최대 신고 수, 없으면 1)
        int maxReportCount = reviewPage.getContent().stream().mapToInt(ReportedReviewDTO::getReportCount).max().orElse(1);

        model.addAttribute("reviews", reviewPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reviewPage.getTotalPages());
        model.addAttribute("unprocessedCount", unprocessedCount);
        model.addAttribute("processedCount", processedCount);
        model.addAttribute("searchCriteria", criteria);
        model.addAttribute("maxReportCount", maxReportCount);

        return "domain/admin/admin_revreportlist";  // 템플릿 경로에 맞게 작성 (예: src/main/resources/templates/domain/admin/admin_revreportlist.html)
    }

    @GetMapping("/admin_revreportdetail")
    public String getReportedReviewDetail(@RequestParam("reviewCode") int reviewCode, Model model) {
        ReportedReviewDTO reviewDTO = reportService.getReportedReviewDetail(reviewCode);
        model.addAttribute("review", reviewDTO);
        return "domain/admin/admin_revreportdetail";  // 신고 상세 페이지 템플릿
    }

    @PostMapping("/updateReviewStatus")
    public String updateReviewStatus(@RequestParam int reviewCode) {
        reportService.toggleHiddenStatus(reviewCode);
        return "redirect:/admin/admin_revreportdetail?reviewCode=" + reviewCode;
    }
}

