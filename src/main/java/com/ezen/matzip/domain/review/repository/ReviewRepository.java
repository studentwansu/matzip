package com.ezen.matzip.domain.review.repository;

import com.ezen.matzip.domain.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    List<ReviewEntity> findByRestaurantCode();
}
