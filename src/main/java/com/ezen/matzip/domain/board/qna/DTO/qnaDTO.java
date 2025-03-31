package com.ezen.matzip.domain.board.qna.DTO;

import com.ezen.matzip.domain.board.qna.entity.qnaEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class qnaDTO {

    private String id;
    private String title;
    private String content;
    private String writer;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;

    public qnaDTO(qnaEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.answer = entity.getAnswer();
        this.createdAt = entity.getCreatedAt();
        this.answeredAt = entity.getAnsweredAt();
    }
}

