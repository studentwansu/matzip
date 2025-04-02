package com.ezen.matzip.domain.minigame.service;

import com.ezen.matzip.domain.weather.dto.KeywordDTO;
import com.ezen.matzip.domain.weather.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MiniGameService {

    private final KeywordRepository keywordRepository;

    public List<KeywordDTO> sortKeywords(List<KeywordDTO> keywords) {
        return null;
    }

}
