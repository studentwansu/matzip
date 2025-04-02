package com.ezen.matzip.domain.user.repository;

import com.ezen.matzip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);
//    boolean existsByUserId(String userId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userId = :userId")
    boolean existsByUserId(@Param("userId") String userId);

    // ★ 추가: 이름, 질문, 답변까지 함께 조회
    Optional<User> findByUserIdAndNameAndPasswordQuestionAndPasswordAnswer(
            String userId,
            String name,
            String passwordQuestion,
            String passwordAnswer
    );

    boolean existsByEmailAndUserIdNot(String email, String userId);
    boolean existsByPhoneNumberAndUserIdNot(String phoneNumber, String userId);
}
