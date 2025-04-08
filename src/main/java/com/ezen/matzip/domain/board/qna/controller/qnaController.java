package com.ezen.matzip.domain.board.qna.controller;

import com.ezen.matzip.domain.board.qna.DTO.qnaDTO;
import com.ezen.matzip.domain.board.qna.service.qnaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class qnaController {

    private final qnaService qnaService;

    public qnaController(qnaService qnaService) {
        this.qnaService = qnaService;
    }

    // ========== 사용자 전용 QnA ==========
    // 사용자가 본인의 QnA 목록을 조회
    @GetMapping("/board/qna")
    public String userQnaList(Model model,
                              @AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size) {
        String writer = userDetails.getUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<qnaDTO> pageResult = qnaService.getByWriter(writer, pageable);

        model.addAttribute("page", pageResult);
        model.addAttribute("qnaList", pageResult.getContent());
        return "domain/board/qna/user_qna_list";
    }


    // 사용자가 본인이 작성한 QnA 상세보기 (다른 사용자의 글은 접근 불가)
    @GetMapping("/board/qna/{id}")
    public String userQnaDetail(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        qnaDTO qna = qnaService.getById(id);
        if (!qna.getWriter().equals(userDetails.getUsername())) {
            return "error404";
        }
        model.addAttribute("qna", qna);
        return "domain/board/qna/user_qna_detail";  // 수정: 사용자 전용 상세보기 뷰
    }

    // 사용자가 QnA 작성 폼 표시
    @GetMapping("/board/qna/write")
    public String showWriteForm() {
        return "domain/board/qna/user_qna_write_form";  // 수정: 사용자 전용 작성 폼 뷰
    }

    // 사용자가 QnA를 작성
    @PostMapping("/board/qna/write")
    public String createUserQna(@RequestParam String title,
                                @RequestParam String content,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        String writer = userDetails.getUsername();
        qnaService.create(title, content, writer);
        // 등록 성공 시 플래시 속성 추가
        redirectAttributes.addFlashAttribute("success", "등록 성공");
        return "redirect:/board/qna";
    }

    // ========== 관리자 전용 QnA ==========
    // 관리자가 전체 QnA 목록을 조회 (공지사항, FAQ 등과 경로 일치)
    @GetMapping("/admin/board/qna")
    public String adminQnaList(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<qnaDTO> pageResult = qnaService.getAll(pageable);

        model.addAttribute("page", pageResult);
        model.addAttribute("qnaList", pageResult.getContent());
        return "domain/board/qna/admin_qna_list";
    }

    // 관리자가 특정 QnA 상세보기 (관리자 전용 뷰 사용)
    @GetMapping("/admin/board/qna/{id}")
    public String adminQnaDetail(@PathVariable String id, Model model) {
        qnaDTO qna = qnaService.getById(id);
        model.addAttribute("qna", qna);
        return "domain/board/qna/admin_qna_write_form";  // 관리자 전용 상세보기 뷰
    }

    // 관리자가 QnA에 답변을 수정/등록 (공지사항, FAQ의 edit 경로와 동일한 패턴)
    @PostMapping("/admin/board/qna/edit/{id}")
    public String editAdminQna(@PathVariable String id,
                               @RequestParam String answer) {
        qnaService.updateAnswer(id, answer);
        return "redirect:/admin/board/qna";
    }
    // ========== 관리자 전용 QnA 답변 작성 폼 ==========
    @GetMapping("/admin/board/qna/write/{id}")
    public String showAdminWriteForm(@PathVariable String id, Model model) {
        qnaDTO qna = qnaService.getById(id);
        model.addAttribute("qna", qna);
        // 실제 파일명이 admin_qna_write_form.html 이므로 뷰 이름도 그대로
        return "domain/board/qna/admin_qna_write_form";
    }

}
