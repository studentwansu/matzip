package com.ezen.matzip.domain.weather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherDTO {

    private int weatherCode;
    private String weatherCondition;

}
