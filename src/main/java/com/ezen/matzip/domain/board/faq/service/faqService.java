package com.ezen.matzip.domain.board.faq.service;

import com.ezen.matzip.domain.board.faq.repository.FaqRepository;
import com.ezen.matzip.domain.board.faq.DTO.faqDTO;
import com.ezen.matzip.domain.board.faq.entity.FaqEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class faqService {

    private final FaqRepository faqRepository;

    public faqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<faqDTO> getAll() {
        return faqRepository.findAll().stream()
                .map(faqDTO::new)
                .collect(Collectors.toList());
    }

    public void create(String title, String content) {
        FaqEntity faq = new FaqEntity(title, content);
        faqRepository.save(faq);
    }

    public void delete(String id) {
        faqRepository.deleteById(id);
    }

    public faqDTO getById(String id) {
        return faqRepository.findById(id)
                .map(faqDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("FAQ가 존재하지 않습니다."));
    }

    // update 메서드 추가 (관리자 전용)
    public void update(String id, String title, String content) {
        FaqEntity faq = faqRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FAQ가 존재하지 않습니다."));
        faq.update(title, content);
        faqRepository.save(faq);
    }
}

