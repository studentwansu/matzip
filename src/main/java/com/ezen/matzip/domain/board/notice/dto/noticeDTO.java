package com.ezen.matzip.domain.board.notice.dto;

import com.ezen.matzip.domain.board.notice.entity.NoticeEntity;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class noticeDTO {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public noticeDTO(NoticeEntity notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
    }
}