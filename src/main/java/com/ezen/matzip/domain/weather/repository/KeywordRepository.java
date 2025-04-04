package com.ezen.matzip.domain.weather.repository;

import com.ezen.matzip.domain.weather.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    @Query("SELECT k FROM Keyword k JOIN k.weatherKeywords wk WHERE wk.weather.weatherCode = :weatherCode")
    List<Keyword> findByWeatherCode(@Param("weatherCode") Integer weatherCode);

    List<Keyword> findAllByKeywordDescriptionIsNotNull();

    Keyword findByKeyword(String keyword);
}
