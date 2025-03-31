package com.ezen.matzip.domain.weather.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weather_keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int weatherKeywordCode;

    @ManyToOne
    @JoinColumn(name = "weather_code")
    private Weather weather;

    @ManyToOne
    @JoinColumn(name = "keyword_code")
    private Keyword keyword;
}
