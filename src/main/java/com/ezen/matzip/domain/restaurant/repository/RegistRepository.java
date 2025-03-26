package com.ezen.matzip.domain.restaurant.repository;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistRepository extends JpaRepository <Restaurant, Integer> {

}
