package com.ezen.matzip.domain.user.repository;

import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository  extends JpaRepository<Business, String> {

    Optional<Business> findByBusinessid(String id);
    boolean existsByBusinessid(String id);
}

//사업자 DB 접근