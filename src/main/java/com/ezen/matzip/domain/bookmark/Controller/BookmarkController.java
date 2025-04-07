package com.ezen.matzip.domain.bookmark.Controller;

import com.ezen.matzip.domain.bookmark.entity.Bookmark;
import com.ezen.matzip.domain.bookmark.service.BookmarkService;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.service.RestaurantService;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final UserService userService;
    private final BookmarkService bookmarkService;
    private final RestaurantService restaurantService;

    @PostMapping("/toggle")
    public String toggleBookmark(@RequestParam("restaurantCode") int restaurantCode, @RequestParam(value = "redirectUrl", required = false) String redirectUrl, Principal principal) {
        User user = userService.findByUserId(principal.getName());
        Restaurant restaurant = restaurantService.findByRestaurantCode(restaurantCode);

        // 이미 북마크되어 있으면 삭제, 아니면 추가
        if (bookmarkService.isBookmarked(user, restaurant)) {
            Bookmark bookmark = bookmarkService.findByUserAndRestaurant(user, restaurant).orElseThrow(() -> new RuntimeException("북마크 정보를 찾을 수 없습니다."));
            bookmarkService.deleteBookmark(bookmark.getBookmarkCode());
        } else {
            Bookmark bookmark = new Bookmark();
            bookmark.setUser(user);
            bookmark.setRestaurant(restaurant);
            bookmarkService.addBookmark(bookmark);
        }

        // redirectUrl 파라미터가 있으면 해당 URL로, 없으면 기본값으로 리다이렉트
        return "redirect:" + (redirectUrl != null && !redirectUrl.isEmpty() ? redirectUrl : "/restaurants");
    }

    // 북마크 삭제
    @PostMapping("/{bookmarkCode}/delete")
    public String deleteBookmark(@PathVariable("bookmarkCode") Long bookmarkCode, Principal principal) {
        // 필요시 소유자 확인 로직 추가
        bookmarkService.deleteBookmark(bookmarkCode);
        return "redirect:/user/bookmarks";
    }

    @GetMapping
    public String listBookmarks(Model model, Principal principal,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size) {
        User user = userService.findByUserId(principal.getName());
        Page<Bookmark> bookmarkPage = bookmarkService.getBookmarksForUser(user, page, size);

        model.addAttribute("bookmarkPage", bookmarkPage);
        model.addAttribute("bookmarks", bookmarkPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookmarkPage.getTotalPages());

        return "domain/bookmark/user_bookmark_list";
    }
}
