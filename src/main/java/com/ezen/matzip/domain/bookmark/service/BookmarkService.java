package com.ezen.matzip.domain.bookmark.service;

import com.ezen.matzip.domain.bookmark.entity.Bookmark;
import com.ezen.matzip.domain.bookmark.repository.BookmarkRepository;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public List<Bookmark> getBookmarksForUser(User user) {
        return bookmarkRepository.findByUser(user);
    }

    public Bookmark addBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(Long bookmarkCode) {
        bookmarkRepository.deleteById(bookmarkCode);
    }

    public boolean isBookmarked(User user, Restaurant restaurant) {
        return bookmarkRepository.existsByUserAndRestaurant(user, restaurant);
    }
}
