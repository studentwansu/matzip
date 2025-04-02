package com.ezen.matzip.domain.user.repository;

import com.ezen.matzip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);

    // ★ 추가: 이름, 질문, 답변까지 함께 조회
    Optional<User> findByUserIdAndNameAndPasswordQuestionAndPasswordAnswer(
            String userId,
            String name,
            String passwordQuestion,
            String passwordAnswer
    );

    }
