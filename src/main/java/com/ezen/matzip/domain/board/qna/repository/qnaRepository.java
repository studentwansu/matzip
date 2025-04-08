package com.ezen.matzip.domain.board.qna.repository;

import com.ezen.matzip.domain.board.qna.entity.qnaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface qnaRepository extends JpaRepository<qnaEntity, String> {
    List<qnaEntity> findByWriter(String writer);

    int countByAnswerIsNull();

    List<qnaEntity> findByWriterOrderByCreatedAtDesc(String writer);

    List<qnaEntity> findAllByOrderByCreatedAtDesc();

    Page<qnaEntity> findByWriter(String writer, Pageable pageable);
}
