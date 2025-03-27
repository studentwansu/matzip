package com.ezen.matzip.domain.board.qna.controller;
import com.ezen.matzip.domain.board.qna.DTO.qnaDTO;
import com.ezen.matzip.domain.board.qna.service.qnaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/board/qna")
public class qnaController {

    private final qnaService qnaService;

    public qnaController(qnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping
    public String list(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String writer = userDetails.getUsername(); // 로그인 사용자 ID (Spring Security 사용 시)
        List<qnaDTO> qnaList = qnaService.getByWriter(writer);
        model.addAttribute("qnaList", qnaList);
        return "board/qna/qna_list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        qnaDTO qna = qnaService.getById(id);

        if (!qna.getWriter().equals(userDetails.getUsername())) {
            return "error/404";
        }

        model.addAttribute("qna", qna);
        return "board/qna/qna_detail_user";
    }
    @GetMapping("/write")
    public String showWriteForm() {
        return "board/qna/qna_write_form";
    }

    @PostMapping("/write")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam String writer) {
        qnaService.create(title, content, writer);
        return "redirect:/board/qna/qna_list";
    }

}


