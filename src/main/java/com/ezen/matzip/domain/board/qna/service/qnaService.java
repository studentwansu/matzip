package com.ezen.matzip.domain.board.qna.service;

import com.ezen.matzip.domain.board.qna.DTO.qnaDTO;
import com.ezen.matzip.domain.board.qna.entity.qnaEntity;
import com.ezen.matzip.domain.board.qna.repository.qnaRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class qnaService {

    private final qnaRepository qnaRepository;

    public qnaService(qnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    public List<qnaDTO> getAll() {
        return qnaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(qnaDTO::new)
                .collect(Collectors.toList());
    }

    public void create(String title, String content, String writer) {
        qnaEntity qna = new qnaEntity(title, content, writer);
        qnaRepository.save(qna);
    }

    public void delete(String id) {
        qnaRepository.deleteById(id);
    }

    public List<qnaDTO> getByWriter(String writer) {
        return qnaRepository.findByWriterOrderByCreatedAtDesc(writer)
                .stream()
                .map(qnaDTO::new)
                .collect(Collectors.toList());
    }

    public qnaDTO getById(String id) {
        return qnaRepository.findById(id)
                .map(qnaDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Q&A가 존재하지 않습니다."));
    }

    // 관리자 기능: 답변 수정 메소드 추가
    public void updateAnswer(String id, String answer) {
        qnaEntity qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Q&A가 존재하지 않습니다."));
        qna.writeAnswer(answer);
        qnaRepository.save(qna);
    }


}


