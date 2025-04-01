package com.ezen.matzip.domain.weather.controller;

import com.ezen.matzip.domain.weather.dto.KeywordDTO;
import com.ezen.matzip.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeatherKeyword(@RequestParam String weatherKeyword, Model model)
    {
        List<KeywordDTO> keywords = weatherService.findKeywordsByWeatherCondition("Clear");
        model.addAttribute("recommendKeywords", keywords);
        model.addAttribute("weatherCondition", weatherKeyword);
        return "main/main";
    }
}

//weather/ → 날씨 기반 추천 기능

//Controller → 클라이언트 요청을 처리
//Service → 비즈니스 로직을 처리
//Repository → 데이터베이스 CRUD 처리
//DTO → 클라이언트와 데이터를 주고받을 때 사용하는 객체
//Entity → 실제 데이터베이스 테이블과 매칭되는 객체
