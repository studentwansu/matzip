package com.ezen.matzip.domain.weather.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weather")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Weather {
    @Id
    private int weatherCode;
    private String weatherCondition;

    @OneToMany(mappedBy = "weather")
    private List<WeatherKeyword> weatherKeywords = new ArrayList<>();
}
