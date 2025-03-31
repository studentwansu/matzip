package com.ezen.matzip.domain.weather.service;

import com.ezen.matzip.domain.weather.dto.KeywordDTO;
import com.ezen.matzip.domain.weather.entity.Keyword;
import com.ezen.matzip.domain.weather.entity.Weather;
import com.ezen.matzip.domain.weather.repository.KeywordRepository;
import com.ezen.matzip.domain.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final KeywordRepository keywordRepository;
    private final ModelMapper modelMapper;

    public List<KeywordDTO> findKeywordsByWeatherCondition(String weatherCondition) {
        Weather weather = weatherRepository.findByWeatherCondition(weatherCondition);
        List<Keyword> keywords = keywordRepository.findByWeatherCode(weather.getWeatherCode());

        List<KeywordDTO> result = new ArrayList<>();
        List<Integer> rand = new ArrayList<>();

        while(rand.size() < 5)
        {
            int num = (int) (Math.random() * keywords.size());
            if(!rand.contains(num))
            {
                rand.add(num);
                Keyword keyword = keywords.get(num);
                result.add(modelMapper.map(keyword, KeywordDTO.class));
            }
        }

        return result;
    }

    public String weatherKeyword(String weatherCondition)
    {
        switch(weatherCondition)
        {
            case "Thunderstorm":
                return "#천둥번개⛈️ #으슬으슬❄️ #Thunderstorm⛈️";
            case "Snow":
                return "#눈송이❄️ #로맨틱💞 #덜덜🥶 #Snow❄️";
            case "Rain":
                return "#비☔ #축축해🌧️ #분위기☕ #Rain☔";
            case "Drizzle":
                return "#보슬비🌦️ #분위기☕ #감성충만✨ #Drizzle🌦️";
            case "Clouds":
                return "#구름많음☁️ #잔잔한하루😌 #소풍갈까🧺 #Clouds☁️";
            case "Clear":
                return "#맑음☀️ #화창한날😎 #산책가자🚶‍♀️ #Clear☀️";
            case "Atmosphere":
                return "#안개🌫️ #몽환적인🌁 #신비로움✨ #Foggy🌫️";
        }
        return "";
    }
}