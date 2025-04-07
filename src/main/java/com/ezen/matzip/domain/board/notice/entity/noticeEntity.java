package com.ezen.matzip.domain.board.notice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "notice")
public class noticeEntity {

    @Id
    private String id;
    @Column(name = "notice_title")
    private String title;
    @Column(name = "notice_content")
    private String content;
    @Column(name = "notice_created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
    }

    // 생성자
    public noticeEntity(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // 공지사항 수정
    public void updateNotice(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
