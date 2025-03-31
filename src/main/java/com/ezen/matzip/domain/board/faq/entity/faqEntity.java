package com.ezen.matzip.domain.board.faq.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "faq")
@Getter
@NoArgsConstructor
public class faqEntity {

    @Id
    private String id;
    @Column(name = "faq_title")
    private String title;
    @Column(name = "faq_content")
    private String content;
    @Column(name = "faq_created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
    }

    public faqEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

