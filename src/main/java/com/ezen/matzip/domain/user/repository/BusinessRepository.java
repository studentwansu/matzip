package com.ezen.matzip.domain.user.repository;

import com.ezen.matzip.domain.user.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Optional<Business> findByUserId(String userId);

    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByBusinessNumber(String businessNumber);
    boolean existsByRestaurantName(String restaurantName);
    boolean existsByPhoneNumber(String phoneNumber);
}
