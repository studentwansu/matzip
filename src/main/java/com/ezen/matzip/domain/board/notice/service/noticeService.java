package com.ezen.matzip.domain.board.notice.service;

import com.ezen.matzip.domain.board.notice.dto.noticeDTO;
import com.ezen.matzip.domain.board.notice.entity.noticeEntity;
import com.ezen.matzip.domain.board.notice.repository.noticeRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;



@Service
public class noticeService {

    private final noticeRepository noticeRepository;

    public noticeService(noticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<noticeDTO> getAllNotices() {
        List<noticeEntity> notices = noticeRepository.findAll();
        return notices.stream().map(noticeDTO::new).collect(Collectors.toList());
    }

    public noticeDTO getNoticeById(String id) {
        noticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(" 해당 공지사항이 존재하지 않습니다."));
        return new noticeDTO(notice);
    }

    public void createNotice(String title, String content) {
        noticeEntity notice = new noticeEntity(title, content);
        noticeRepository.save(notice);
    }

    public void updateNotice(String id, String title, String content) {
        noticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(" 해당 공지사항이 존재하지 않습니다."));
        notice.updateNotice(title, content);
        noticeRepository.save(notice);
    }

    public void deleteNotice(String id) {
        noticeRepository.deleteById(id);
    }
}
