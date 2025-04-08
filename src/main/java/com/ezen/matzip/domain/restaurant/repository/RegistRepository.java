package com.ezen.matzip.domain.restaurant.repository;

import com.ezen.matzip.domain.restaurant.entity.Regist;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistRepository extends JpaRepository <Regist, Integer> {

//    Regist findByBusinessCode(int businessCode);

}

