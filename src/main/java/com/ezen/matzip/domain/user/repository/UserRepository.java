package com.ezen.matzip.domain.user.repository;

import com.ezen.matzip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserid(String id);
    boolean existsByUserid(String id);
}

