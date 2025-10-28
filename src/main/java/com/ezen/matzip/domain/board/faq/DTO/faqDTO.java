package com.ezen.matzip.domain.board.faq.DTO;

import com.ezen.matzip.domain.board.faq.entity.FaqEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class faqDTO {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public faqDTO(FaqEntity faq) {
        this.id = faq.getId();
        this.title = faq.getTitle();
        this.content = faq.getContent();
        this.createdAt = faq.getCreatedAt();
    }
}

