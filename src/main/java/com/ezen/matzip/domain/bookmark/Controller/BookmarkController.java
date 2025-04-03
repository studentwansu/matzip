package com.ezen.matzip.domain.bookmark.Controller;

import com.ezen.matzip.domain.bookmark.entity.Bookmark;
import com.ezen.matzip.domain.bookmark.service.BookmarkService;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.service.RestaurantService;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final UserService userService;
    private final BookmarkService bookmarkService;
    private final RestaurantService restaurantService;

    // 북마크 목록 조회 (유저 전용)
    @GetMapping
    public String listBookmarks(Model model, Principal principal) {
        User user = userService.findByUserId(principal.getName());
        List<Bookmark> bookmarks = bookmarkService.getBookmarksForUser(user);
        model.addAttribute("bookmarks", bookmarks);
        return "domain/bookmark/user_bookmark_list";
    }

    // 북마크 추가 (식당 목록, 상세 페이지 등에서)
    @PostMapping("/add")
    public String addBookmark(@RequestParam("restaurantCode") int restaurantCode, Principal principal) {
        User user = userService.findByUserId(principal.getName());
        Restaurant restaurant = restaurantService.findByRestaurantCode(restaurantCode);
        if (!bookmarkService.isBookmarked(user, restaurant)) {
            Bookmark bookmark = new Bookmark();
            bookmark.setUser(user);
            bookmark.setRestaurant(restaurant);
            bookmarkService.addBookmark(bookmark);
        }
        // 북마크 추가 후, 식당 상세 페이지로 리다이렉트
        return "redirect:/storeinfo?restaurantCode=" + restaurantCode;
    }

    // 북마크 삭제
    @PostMapping("/{bookmarkCode}/delete")
    public String deleteBookmark(@PathVariable("bookmarkCode") Long bookmarkCode, Principal principal) {
        // 필요시 소유자 확인 로직 추가
        bookmarkService.deleteBookmark(bookmarkCode);
        return "redirect:/user/bookmarks";
    }
}
