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
    public List<KeywordDTO>[] shuffleKeywords() {

        List<Keyword> keywordEntities = keywordRepository.findAllByKeywordDescriptionIsNotNull();
        Collections.shuffle(keywordEntities);
        keywordEntities.remove(keywordEntities.get(0));
        List<KeywordDTO>[] keywords = new List[3];
        keywords[0] = new ArrayList<>();
        keywords[1] = new ArrayList<>();
        keywords[2] = new ArrayList<>();
        for (Keyword keyword : keywordEntities) {
            keywords[0].add(modelMapper.map(keyword, KeywordDTO.class));
        }
        return keywords;
    }

    // 2. 두 개의 키워드 내보내기
    public List<KeywordDTO>[] showKeywords(List<KeywordDTO>[] keywords)
    {
        if(keywords[0].size() + keywords[1].size() + keywords[2].size() > 1)
        {
            keywords[1].add(keywords[0].get(0));
            keywords[0].remove(keywords[0].get(0));

            keywords[1].add(keywords[0].get(0));
            keywords[0].remove(keywords[0].get(0));
        }

        return keywords;
    }

    public String checkCurrentRound(List<KeywordDTO>[] keywords)
    {
        if (keywords[0].size() > 2 && keywords[2].isEmpty())
        {
            return (keywords[0].size()) + "강 시작!";
        }
        else if (keywords[0].size() == 2 && keywords[2].isEmpty())
        {
            return "결승!";
        }
        else if (keywords[0].isEmpty() && keywords[2].size() == 1)
        {
            return "끝!";
        }

            return null;
    }

    public List<KeywordDTO>[] sortKeyword(List<KeywordDTO>[] remainedKeywords, String selected) {

        Keyword foundKeyword = keywordRepository.findByKeyword(selected);
        remainedKeywords[2].add(modelMapper.map(foundKeyword, KeywordDTO.class));
        remainedKeywords[1].clear();

        if (remainedKeywords[0].isEmpty() && remainedKeywords[2].size() > 1)
        {
            remainedKeywords[0].addAll(remainedKeywords[2]);
            remainedKeywords[2].clear();
            Collections.shuffle(remainedKeywords[0]);
        }
        return remainedKeywords;
    }

    public KeywordDTO foundKeyword(String selected)
    {
        return modelMapper.map(keywordRepository.findByKeyword(selected), KeywordDTO.class);
    }

}
