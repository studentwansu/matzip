package com.ezen.matzip.domain.board.qna.repository;

import com.ezen.matzip.domain.board.qna.entity.QnaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<QnaEntity, String> {
    List<QnaEntity> findByWriter(String writer);

    int countByAnswerIsNull();

    List<QnaEntity> findByWriterOrderByCreatedAtDesc(String writer);

    List<QnaEntity> findAllByOrderByCreatedAtDesc();

    Page<QnaEntity> findByWriter(String writer, Pageable pageable);
}
