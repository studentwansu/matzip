package com.ezen.matzip.domain.board.faq.controller;

import com.ezen.matzip.domain.board.faq.DTO.faqDTO;
import com.ezen.matzip.domain.board.faq.service.faqService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping()
public class faqController {

    private final faqService faqService;

    public faqController(faqService faqService) {

        this.faqService = faqService;
    }

    @GetMapping("/board/faq/list")
    public String list(Model model, Authentication authentication) {
        model.addAttribute("faqList", faqService.getAll());

        // 관리자이면 관리자용 목록 페이지를 반환
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "domain/board/faq/faq_admin_list";
        }
        return "domain/board/faq/faq_list";
    }

    // FAQ 상세 조회 (모든 사용자 접근)
    @GetMapping("/board/faq/{id}")
    public String detail(@PathVariable String id, Model model) {
        faqDTO faq = faqService.getById(id);
        model.addAttribute("faq", faq);
        return "domain/board/faq/faq_detail";
    }

    // FAQ 등록 폼 (관리자 전용)
    @GetMapping("/admin/board/faq/write")
    public String writeForm() {
        return "domain/board/faq/faq_write_form";
    }

    // FAQ 등록 처리 (관리자 전용)
    @PostMapping("/admin/board/faq/create")
    public String create(@RequestParam String title, @RequestParam String content) {
        faqService.create(title, content);
        return "redirect:/board/faq/list";
    }

    // FAQ 수정 폼 (관리자 전용)
    @GetMapping("/admin/board/faq/edit/{id}")
    public String updateForm(@PathVariable String id, Model model) {
        faqDTO faq = faqService.getById(id);
        model.addAttribute("faq", faq);
        return "domain/board/faq/faq_edit";
    }

    // FAQ 수정 처리 (관리자 전용)
    @PostMapping("/admin/board/faq/edit/{id}")
    public String update(@PathVariable String id, @RequestParam String title, @RequestParam String content) {
        faqService.update(id, title, content);
        return "redirect:/board/faq/list";
    }

    // FAQ 삭제 처리 (관리자 전용)
    @PostMapping("/admin/board/faq/delete/{id}")
    public String delete(@PathVariable String id) {
        faqService.delete(id);
        return "redirect:/board/faq/list";
    }
}


