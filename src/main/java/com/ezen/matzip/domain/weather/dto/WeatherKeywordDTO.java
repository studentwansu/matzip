package com.ezen.matzip.domain.weather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherKeywordDTO {
    private int weatherKeywordCode;
    private int weatherCode;
    private int keywordCode;
}
