package com.ezen.matzip.domain.board.faq.repository;

import com.ezen.matzip.domain.board.faq.entity.faqEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface faqRepository extends JpaRepository<faqEntity, String> {
}
