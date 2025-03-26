package com.ezen.matzip.domain.board.faq.service;
import com.ezen.matzip.domain.board.faq.repository.faqRepository;
import com.ezen.matzip.domain.board.faq.DTO.faqDTO;
import com.ezen.matzip.domain.board.faq.entity.faqEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class faqService {

    private final faqRepository faqRepository;

    public faqService(faqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<faqDTO> getAll() {
        return faqRepository.findAll().stream()
                .map(faqDTO::new).collect(Collectors.toList());
    }

    public void create(String title, String content) {
        faqEntity faq = new faqEntity(title, content);
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
}

