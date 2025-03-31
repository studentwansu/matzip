package com.ezen.matzip.domain.board.notice.Controller;

import com.ezen.matzip.domain.board.notice.dto.noticeDTO;
import com.ezen.matzip.domain.board.notice.service.noticeService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board/notice")
public class noticeController {

    private final noticeService noticeService;

    public noticeController(noticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public String getAllNotices(Model model) {
        List<noticeDTO> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "board/notice/notice_list";
    }

    @GetMapping("/{id}")
    public String getNoticeById(@PathVariable String id, Model model) {
        noticeDTO notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "board/notice/notice_detail";
    }

    @GetMapping("/create")
    public String createNoticeForm() {
        return "board/notice/notice_form";
    }


    @PostMapping("/create")
    public String createNotice(@RequestParam String title, @RequestParam String content) {
        noticeService.createNotice(title, content);
        return "redirect:/board/notice";
    }


    @GetMapping("/edit/{id}")
    public String updateNoticeForm(@PathVariable String id, Model model) {
        noticeDTO notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "board/notice/notice_edit";
    }


    @PostMapping("/edit/{id}")
    public String updateNotice(@PathVariable String id, @RequestParam String title, @RequestParam String content) {
        noticeService.updateNotice(id, title, content);
        return "redirect:/board/notice";
    }


    @PostMapping("/delete/{id}")
    public String deleteNotice(@PathVariable String id) {
        noticeService.deleteNotice(id);
        return "redirect:/board/notice";
    }

    @GetMapping("/write")
    public String showWriteForm() {
        return "board/notice/notice_write_form";  // templates/notice/notice_write_form.html
    }

    @PostMapping("/write")
    public String create(@RequestParam String title, @RequestParam String content) {
        noticeService.createNotice(title, content);  // DB에 저장
        return "redirect:/board/notice";  // 목록 페이지로 리다이렉트
    }

    @GetMapping("/detail/{id}")
    public String showDetailPage(@PathVariable String id, Model model) {
        noticeDTO notice = noticeService.getNoticeById(id); // ← 이 부분
        model.addAttribute("notice", notice);
        return "board/notice/notice_detail";
    }


}
