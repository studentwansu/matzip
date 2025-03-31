package com.ezen.matzip.domain.weather.repository;

import com.ezen.matzip.domain.weather.entity.Keyword;
import com.ezen.matzip.domain.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    Weather findByWeatherCondition(String condition);
}
