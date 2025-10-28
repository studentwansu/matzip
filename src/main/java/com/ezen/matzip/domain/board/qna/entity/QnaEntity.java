package com.ezen.matzip.domain.board.qna.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "qna")
@Getter
@NoArgsConstructor
public class QnaEntity {

    @Id
    private String id;

    private String title;
    private String content;
    private String writer;

    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public QnaEntity(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void writeAnswer(String answer) {
        this.answer = answer;
        this.answeredAt = LocalDateTime.now();
    }
}

