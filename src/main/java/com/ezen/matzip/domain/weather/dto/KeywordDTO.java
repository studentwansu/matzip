package com.ezen.matzip.domain.weather.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KeywordDTO {

    private int KeywordCode;
    private String Keyword;
    private String KeywordImgPath;
    private String KeywordDescription;
}
