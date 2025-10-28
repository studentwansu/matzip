package com.ezen.matzip.domain.board.qna.service;

import com.ezen.matzip.domain.board.qna.DTO.qnaDTO;
import com.ezen.matzip.domain.board.qna.entity.QnaEntity;
import com.ezen.matzip.domain.board.qna.repository.QnaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class qnaService {

    private final QnaRepository qnaRepository;

    public qnaService(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    public List<qnaDTO> getAll() {
        return qnaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(qnaDTO::new)
                .collect(Collectors.toList());
    }

    public void create(String title, String content, String writer) {
        QnaEntity qna = new QnaEntity(title, content, writer);
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
        QnaEntity qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Q&A가 존재하지 않습니다."));
        qna.writeAnswer(answer);
        qnaRepository.save(qna);
    }
    // 전체 페이징 조회 (관리자)
    public Page<qnaDTO> getAll(Pageable pageable) {
        return qnaRepository.findAll(pageable)
                .map(qnaDTO::new);
    }

    // 사용자별 페이징 조회
    public Page<qnaDTO> getByWriter(String writer, Pageable pageable) {
        return qnaRepository.findByWriter(writer, pageable)
                .map(qnaDTO::new);
    }
}


