package com.ezen.matzip.domain.board.faq.repository;

import com.ezen.matzip.domain.board.faq.entity.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<FaqEntity, String> {
}
