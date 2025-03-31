package com.ezen.matzip.domain.board.faq.controller;

import com.ezen.matzip.domain.board.faq.DTO.faqDTO;
import com.ezen.matzip.domain.board.faq.service.faqService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/board/faq")
public class faqController {

    private final faqService faqService;

    public faqController(faqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("faqList", faqService.getAll());
        return "board/faq/faq_list"; // html 파일 경로
    }

    @PostMapping("/write")
    public String create(@RequestParam String title, @RequestParam String content) {
        faqService.create(title, content);
        return "redirect:/board/faq";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        faqService.delete(id);
        return "redirect:/board/faq";
    }

    @GetMapping("/faq")
    public String faqList(Model model) {
        model.addAttribute("faqList", faqService.getAll());
        return "board/faq/faq_list";
    }

    @GetMapping("/write")
    public String writeForm() {
        return "board/faq/faq_write_form";
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model) {
        faqDTO faq = faqService.getById(id);
        model.addAttribute("faq", faq);
        return "board/faq/faq_detail";  // 상세페이지 템플릿
    }
}

