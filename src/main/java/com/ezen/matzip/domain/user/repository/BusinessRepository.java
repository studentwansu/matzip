package com.ezen.matzip.domain.user.repository;

import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Integer> {

    Optional<Business> findByUserId(String userId);

@Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Business b WHERE b.userId = :userId")
boolean existsByUserId(@Param("userId") String userId);


    boolean existsByEmail(String email);
    boolean existsByBusinessNumber(String businessNumber);
    boolean existsByRestaurantName(String restaurantName);
    boolean existsByPhoneNumber(String phoneNumber);

    // 중복 체크: 현재 사업자를 제외하고 같은 이메일이 존재하는지
    boolean existsByEmailAndUserIdNot(String email, String userId);
    // 중복 체크: 현재 사업자를 제외하고 같은 전화번호가 존재하는지
    boolean existsByPhoneNumberAndUserIdNot(String phoneNumber, String userId);


    // ★ 추가: 이름, 질문, 답변까지 함께 조회
    Optional<Business> findByUserIdAndRestaurantNameAndPasswordQuestionAndPasswordAnswer(
            String userId,
            String restaurantName,
            String passwordQuestion,
            String passwordAnswer
    );
}
