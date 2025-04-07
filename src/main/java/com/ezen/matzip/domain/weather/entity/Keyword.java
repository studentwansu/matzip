package com.ezen.matzip.domain.weather.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {
    @Id
    private int keywordCode; // 변수명은 camelCase로 수정
    private String keyword;
    private String keywordImgPath;
    private String keywordDescription;

    @OneToMany(mappedBy = "keyword")
    private List<WeatherKeyword> weatherKeywords = new ArrayList<>();
}