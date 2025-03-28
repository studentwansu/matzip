package com.ezen.matzip.domain.weather.repository;

import com.ezen.matzip.domain.weather.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    List<Keyword> findByWeatherCode(Integer weatherCode);

}
