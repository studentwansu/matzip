package com.ezen.matzip.domain.minigame.service;

import com.ezen.matzip.domain.weather.dto.KeywordDTO;
import com.ezen.matzip.domain.weather.entity.Keyword;
import com.ezen.matzip.domain.weather.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MiniGameService {

    private final KeywordRepository keywordRepository;
    private final ModelMapper modelMapper;

    // 1. 처음 키워드 모은 다음 섞기
    public List<KeywordDTO> shuffleKeywords() {

        List<Keyword> keywords = keywordRepository.findAllByKeywordDescriptionIsNotNull();
        Collections.shuffle(keywords);
        List<KeywordDTO> keywordDTOs = new ArrayList<>();
        for (Keyword keyword : keywords) {
            keywordDTOs.add(modelMapper.map(keyword, KeywordDTO.class));
        }

        return keywordDTOs;
    }

    // 2. 두 개의 키워드 내보내기

    public List<KeywordDTO> showKeywords(List<KeywordDTO> remainedKeywords)
    {
        Collections.shuffle(remainedKeywords);

        List<KeywordDTO> showKeywords = new ArrayList<>();
        showKeywords.add(remainedKeywords.get(0));
        showKeywords.add(remainedKeywords.get(1));

        return showKeywords;
    }


    public List<KeywordDTO> sortKeyword(String selected, String defeated) {


        return null;
    }

    public List<KeywordDTO> miniGame(List<KeywordDTO> remainedKeywords)
    {

        return remainedKeywords;
    }

}
