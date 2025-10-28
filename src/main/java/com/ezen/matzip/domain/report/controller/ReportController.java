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

        // --- 이 부분이 올바르게 수정되었는지 다시 한번 확인해주세요 ---

        // 1. 모델에 'reviews'라는 이름으로 'Page' 객체 전체를 넘겨줍니다.
        model.addAttribute("reviews", reviewPage);

        // 2. ★★★ 모델에 'criteria'라는 이름으로 검색 조건 객체를 넘겨줍니다. ★★★
        model.addAttribute("searchCriteria", criteria);

        // 3. 나머지 필요한 데이터들을 넘겨줍니다.
        model.addAttribute("unprocessedCount", unprocessedCount);
        model.addAttribute("processedCount", processedCount);

        // 페이지네이션용 추가
        model.addAttribute("totalPages", Math.max(1, reviewPage.getTotalPages()));
        model.addAttribute("currentPage", reviewPage.getNumber() + 1);

        // --- 수정 끝 ---

        return "domain/admin/admin_revreportlist";
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

