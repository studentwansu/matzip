package com.ezen.matzip.domain.board.notice.Controller;

import com.ezen.matzip.domain.board.notice.dto.noticeDTO;
import com.ezen.matzip.domain.board.notice.service.noticeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class noticeController {

    private final noticeService noticeService;

    // noticeService를 주입받아 공지사항 관련 비즈니스 로직을 처리
    public noticeController(noticeService noticeService) {
        this.noticeService = noticeService;
    }

    /**
     * 공지사항 목록 조회 메서드 (일반 사용자와 관리자 모두 접근)
     * URL: /board/notice/list
     * - 공지사항 목록을 조회하여 모델에 추가합니다.
     * - 인증 정보에 따라 관리자이면 관리자용 템플릿(notice_admin_list)을, 일반 사용자이면 일반 템플릿(notice_list)을 반환합니다.
     */
    @GetMapping("/board/notice/list")
    public String getAllNotices(Model model, Authentication authentication) {
        List<noticeDTO> notices = noticeService.getAllNotices(); // 모든 공지사항 조회
        model.addAttribute("notices", notices); // 조회한 공지사항 목록을 모델에 추가

        // 인증된 사용자가 관리자 역할을 가지고 있다면 관리자용 목록 페이지 반환
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "domain/board/notice/notice_admin_list";
        }
        // 관리자 외 일반 사용자는 일반 공지사항 목록 페이지 반환
        return "domain/board/notice/notice_list";
    }

    /**
     * 공지사항 상세 조회 메서드
     * URL: /board/notice/{id}
     * - URL 경로의 id 값을 기반으로 해당 공지사항 상세 정보를 조회하고 모델에 추가합니다.
     * - 상세 페이지 템플릿(notice_detail)을 반환합니다.
     */
    @GetMapping("/board/notice/{id}")
    public String getNoticeById(@PathVariable String id, Model model) {
        noticeDTO notice = noticeService.getNoticeById(id); // 공지사항 상세 정보 조회
        model.addAttribute("notice", notice); // 조회한 공지사항 정보를 모델에 추가
        return "domain/board/notice/notice_detail";
    }

    /**
     * 공지사항 등록 폼 반환 메서드 (관리자 전용)
     * URL: /admin/board/notice/write
     * - 공지사항 작성 폼을 보여주는 템플릿(notice_write_form)을 반환합니다.
     */
    @GetMapping("/admin/board/notice/write")
    public String createNoticeForm() {
        return "domain/board/notice/notice_write_form";
    }

    /**
     * 공지사항 등록 처리 메서드 (관리자 전용)
     * URL: /admin/board/notice/create
     * - 폼에서 전달받은 제목과 내용을 이용해 새로운 공지사항을 생성합니다.
     * - 공지사항 등록 후, 등록 폼 페이지로 리다이렉트합니다.
     */
    @PostMapping("/admin/board/notice/create")
    public String createNotice(@RequestParam String title, @RequestParam String content) {
        noticeService.createNotice(title, content); // 새 공지사항 등록 처리
        return "redirect:/admin/board/notice/notice_write_form";
    }

    /**
     * 공지사항 수정 폼 반환 메서드 (관리자 전용)
     * URL: /admin/board/notice/edit/{id}
     * - 수정할 공지사항의 id 값을 이용해 해당 정보를 조회합니다.
     * - 조회한 정보를 모델에 담아 수정 폼 템플릿(notice_edit)을 반환합니다.
     */
    @GetMapping("/admin/board/notice/edit/{id}")
    public String updateNoticeForm(@PathVariable String id, Model model) {
        noticeDTO notice = noticeService.getNoticeById(id); // 수정할 공지사항 정보 조회
        model.addAttribute("notice", notice); // 조회한 정보를 모델에 추가
        return "domain/board/notice/notice_edit";
    }

    /**
     * 공지사항 수정 처리 메서드 (관리자 전용)
     * URL: /admin/board/notice/edit/{id}
     * - 수정 폼에서 전달받은 제목과 내용을 바탕으로 해당 공지사항 정보를 업데이트합니다.
     * - 수정 후 공지사항 목록 페이지로 리다이렉트합니다.
     */
    @PostMapping("/admin/board/notice/edit/{id}")
    public String updateNotice(@PathVariable String id, @RequestParam String title, @RequestParam String content) {
        noticeService.updateNotice(id, title, content); // 공지사항 수정 처리
        return "redirect:/admin/board/notice/list";
    }

    /**
     * 공지사항 삭제 처리 메서드 (관리자 전용)
     * URL: /admin/board/notice/delete/{id}
     * - 전달받은 id 값을 기반으로 해당 공지사항을 삭제합니다.
     * - 삭제 후 공지사항 목록 페이지로 리다이렉트합니다.
     */
    @PostMapping("/admin/board/notice/delete/{id}")
    public String deleteNotice(@PathVariable String id) {
        noticeService.deleteNotice(id); // 공지사항 삭제 처리
        return "redirect:/admin/board/notice/list";
    }

    /**
     * 일반 사용자용 공지사항 목록 페이지 반환 메서드
     * URL: /noticeList
     * - 공지사항 목록 페이지 템플릿(notice_list)을 반환합니다.
     */
    @GetMapping("/noticeList")
    public String noticeList() {
        return "domain/board/notice/notice_list";
    }
}



