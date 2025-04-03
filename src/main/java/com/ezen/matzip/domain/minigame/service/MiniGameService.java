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
        System.out.println("현재 키워드 갯수: " + keywordEntities.size());
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
        if (keywords[0].isEmpty())
        {
            keywords[0].addAll(keywords[2]);
            keywords[2].clear();
            Collections.shuffle(keywords[0]);
        }

        keywords[1].add(keywords[0].get(0));
        keywords[0].remove(keywords[0].get(0));

        keywords[1].add(keywords[0].get(0));
        keywords[0].remove(keywords[0].get(0));

        return keywords;
    }

    public String checkCurrentRound(List<KeywordDTO>[] keywords)
    {
        if (keywords[0].isEmpty() && !keywords[2].isEmpty())
        {
            if (keywords[2].size() == 1)
            {
                return "끝!";
            }

            return (keywords[2].size() + 1) + "강 시작!";
        }
            return null;
    }

    public List<KeywordDTO>[] sortKeyword(List<KeywordDTO>[] remainedKeywords, String selected) {

        if (remainedKeywords.length < 3) {
            // 길이가 3 미만인 경우 새 배열을 생성하고 기존 데이터 복사
            List<KeywordDTO>[] newArray = new List[3];
            for (int i = 0; i < remainedKeywords.length; i++) {
                newArray[i] = remainedKeywords[i];
            }
            for (int i = remainedKeywords.length; i < 3; i++) {
                newArray[i] = new ArrayList<>();
            }
            remainedKeywords = newArray;
        }

        Keyword foundKeyword = keywordRepository.findByKeyword(selected);
        remainedKeywords[2].add(modelMapper.map(foundKeyword, KeywordDTO.class));
        remainedKeywords[1].clear();

        System.out.println("현재 선택된 키워드: " + foundKeyword.getKeyword());
        System.out.println("현재 나오지 않은 키워드: " + remainedKeywords[0]);
        System.out.println("다음 라운드 진출 키워드: " + remainedKeywords[2]);

        return remainedKeywords;
    }

    public KeywordDTO foundKeyword(String selected)
    {
        return modelMapper.map(keywordRepository.findByKeyword(selected), KeywordDTO.class);
    }

}
