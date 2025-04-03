package com.ezen.matzip.domain.bookmark.repository;

import com.ezen.matzip.domain.bookmark.entity.Bookmark;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUser(User user);
    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);
}
