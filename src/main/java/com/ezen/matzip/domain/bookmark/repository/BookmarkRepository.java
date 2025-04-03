package com.ezen.matzip.domain.bookmark.repository;

import com.ezen.matzip.domain.bookmark.entity.Bookmark;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUser(User user);
    Page<Bookmark> findByUser(User user, Pageable pageable);

    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);
    Optional<Bookmark> findByUserAndRestaurant(User user, Restaurant restaurant);
}
