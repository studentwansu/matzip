package com.ezen.matzip.domain.board.notice.repository;

import com.ezen.matzip.domain.board.notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, String> {
}
