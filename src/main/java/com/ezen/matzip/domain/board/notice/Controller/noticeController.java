package com.ezen.matzip.domain.board.notice.Controller;

import com.ezen.matzip.domain.board.notice.dto.noticeDTO;
import com.ezen.matzip.domain.board.notice.service.noticeService;


import lombok.RequiredArgsConstructor;
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

    // 공지사항 목록 페이지: "/board/notice"
    @GetMapping({"", "/","/list"})
    public String getAllNotices(Model model) {
        List<noticeDTO> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "board/notice/notice_list";
    }

    // 공지사항 상세 페이지: "/board/notice/{id}"
    @GetMapping("/{id}")
    public String getNoticeById(@PathVariable String id, Model model) {
        noticeDTO notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "board/notice/notice_detail";
    }

    // 공지사항 생성 폼: "/board/notice/create"
    @GetMapping("/create")
    public String createNoticeForm() {
        return "board/notice/notice_form";
    }

    // 공지사항 생성 처리: "/board/notice/create"
    @PostMapping("/create")
    public String createNotice(@RequestParam String title, @RequestParam String content) {
        noticeService.createNotice(title, content);
        return "redirect:/board/notice";
    }

    // 공지사항 수정 폼: "/board/notice/edit/{id}"
    @GetMapping("/edit/{id}")
    public String updateNoticeForm(@PathVariable String id, Model model) {
        noticeDTO notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "board/notice/notice_edit";
    }

    // 공지사항 수정 처리: "/board/notice/edit/{id}"
    @PostMapping("/edit/{id}")
    public String updateNotice(@PathVariable String id, @RequestParam String title, @RequestParam String content) {
        noticeService.updateNotice(id, title, content);
        return "redirect:/board/notice";
    }

    // 공지사항 삭제 처리: "/board/notice/delete/{id}"
    @PostMapping("/delete/{id}")
    public String deleteNotice(@PathVariable String id) {
        noticeService.deleteNotice(id);
        return "redirect:/board/notice";
    }

    // 공지사항 작성 폼: "/board/notice/write"
    @GetMapping("/write")
    public String showWriteForm() {
        return "board/notice/notice_write_form";
    }

    // 공지사항 작성 처리: "/board/notice/write"
    @PostMapping("/write")
    public String create(@RequestParam String title, @RequestParam String content) {
        noticeService.createNotice(title, content);
        return "redirect:/board/notice";
    }

    // 만약 추가적인 상세 페이지가 필요하다면, 중복되지 않도록 경로를 수정합니다.
    @GetMapping("/detail/{id}")
    public String showDetailPage(@PathVariable String id, Model model) {
        noticeDTO notice = noticeService.getNoticeById(id);
        model.addAttribute("notice", notice);
        return "board/notice/notice_detail";
    }
}

