package com.ezen.matzip.domain.bookmark.service;

import com.ezen.matzip.domain.bookmark.dto.BookmarkDTO;
import com.ezen.matzip.domain.bookmark.dto.RestaurantForBookmarkDTO;
import com.ezen.matzip.domain.bookmark.entity.Bookmark;
import com.ezen.matzip.domain.bookmark.repository.BookmarkRepository;
import com.ezen.matzip.domain.restaurant.dto.MenuDTO;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public RestaurantForBookmarkDTO convertToRestaurantForBookmarkDTO(Restaurant restaurant) {
        RestaurantForBookmarkDTO dto = new RestaurantForBookmarkDTO();
        dto.setRestaurantCode(restaurant.getRestaurantCode());
        dto.setRestaurantName(restaurant.getRestaurantName());
        dto.setMainMenu(restaurant.getMainMenu());
        dto.setRestaurantLocation(restaurant.getRestaurantLocation());
        dto.setRestaurantMenus(
                restaurant.getMenus().stream()
                        .map(menu -> new MenuDTO(menu.getMenuCode(), menu.getMenuName(), menu.getMenuPrice(), restaurant))
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public Optional<Bookmark> findByUserAndRestaurant(User user, Restaurant restaurant) {
        return bookmarkRepository.findByUserAndRestaurant(user, restaurant);
    }

    public Page<Bookmark> getBookmarksForUser(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookmarkRepository.findByUser(user, pageable);
    }
}
