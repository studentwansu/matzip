package com.ezen.matzip.domain.restaurant.controller;

import com.ezen.matzip.domain.bookmark.dto.RestaurantForBookmarkDTO;
import com.ezen.matzip.domain.bookmark.entity.Bookmark;
import com.ezen.matzip.domain.bookmark.service.BookmarkService;
import com.ezen.matzip.domain.restaurant.dto.RegistDTO;
import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import com.ezen.matzip.domain.restaurant.entity.Category;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.service.RestaurantService;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.service.ReviewService;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class RestaurantController {

    @Autowired
    private final RestaurantService restaurantService;
//    private final ReviewService reviewService;

    //완수 북마크 기능에 필요
    @Autowired
    private final BookmarkService bookmarkService;
    @Autowired
    private final UserService userService;


    @GetMapping("/admin/restaurant/{restaurantCode}")
    public String getRestaurantDetail(@PathVariable int restaurantCode, Model model) {
        RestaurantDTO restaurant = restaurantService.getRestaurantDetail(restaurantCode);
        model.addAttribute("restaurant", restaurant);

        List<ReviewDTO> resultReview = restaurantService.getReviewsByRestaurant(restaurantCode);
        model.addAttribute("reviews", resultReview);
        System.out.println("test: " + resultReview);

        return "domain/restaurant/admin_restinfo";
    }

    @GetMapping("/business/restaurant/{restaurantCode}")
    public String getRestaurantDetail2(@PathVariable int restaurantCode, Model model) {
        RestaurantDTO restaurant = restaurantService.getRestaurantDetail(restaurantCode);
        model.addAttribute("restaurant", restaurant);

        List<ReviewDTO> resultReview = restaurantService.getReviewsByRestaurant(restaurantCode);
        model.addAttribute("reviews", resultReview);
        System.out.println("test: " + resultReview);

        return "domain/restaurant/store_restinfo";
    }

    @GetMapping("/business/regist")
    public String registPage() {
        return "domain/store/store_apply";
    }

    @PostMapping("/business/regist")
    public String regist(@ModelAttribute RegistDTO registDTO) {

        System.out.println("=== DTO 로그 ===");
        System.out.println(registDTO.toString());
     restaurantService.registRestaurant(registDTO);

        return "redirect:/restaurant/" + registDTO.getRestaurantCode();
    }


    @GetMapping("/business/modify")
    public String modifyPage() {
        return "restaurant/restaurant-modify";
    }

    @PostMapping("/business/modify")
    public String modify(@ModelAttribute RegistDTO registDTO) {
        System.out.println("modify: " + registDTO.toString());
        restaurantService.modifyRestaurant(registDTO);

        return "redirect:/restaurant/" + registDTO.getRestaurantCode();
    }

    @GetMapping("/search")
    public String findByRestaurant(@RequestParam String keyword, Model model, HttpSession session)
    {
        session.setAttribute("lastKeyword", keyword);
        List<RestaurantDTO> restaurants = restaurantService.findByKeywordOrderByScore(keyword);
        model.addAttribute("restaurantList", restaurants);
        model.addAttribute("myLoc", keyword);

        //완수- 북마크 기능에 필요
        if (principal != null) {
            User user = userService.findByUserId(principal.getName());
            List<Bookmark> bookmarks = bookmarkService.getBookmarksForUser(user);
            Set<Integer> bookmarkedRestaurantCodes = bookmarks.stream()
                    .map(b -> b.getRestaurant().getRestaurantCode())
                    .collect(Collectors.toSet());
            model.addAttribute("bookmarkedRestaurantCodes", bookmarkedRestaurantCodes);
        }

        return "domain/search/user_restlist";
    }

//    @GetMapping("/storeinfo")
//    public String markingLocation(@RequestParam Integer restaurantCode, Model model)
//    {
//        String location = restaurantService.findLocationByRestaurantCode(restaurantCode);
//        model.addAttribute("restaurantLocation", location);
//        return "/domain/restaurant/store_restinfo";
//    }

    @GetMapping(value = "/search", params = "categoryCode")
    public String filteringRestaurants(@RequestParam int categoryCode, Model model, HttpSession session)
    {
        String keyword = (String) session.getAttribute("lastKeyword");
        List<RestaurantDTO> restaurants = restaurantService.filteredRestaurantsByCategory(keyword, categoryCode);
        model.addAttribute("restaurantList", restaurants);
        return "domain/search/user_restlist";
    }

    //완수 북마크 기능에 필요
    // 식당 목록 페이지
    @GetMapping("/restaurants")
    public String restaurantList(Model model, Principal principal) {
        // 전체 식당 목록 조회 (엔티티 목록)
        List<Restaurant> restaurantEntities = restaurantService.findAll();

        // 각 엔티티를 RestaurantForBookmarkDTO로 변환
        List<RestaurantForBookmarkDTO> restaurantDTOs = restaurantEntities.stream()
                .map(restaurant -> restaurantService.convertToRestaurantForBookmarkDTO(restaurant))
                .collect(Collectors.toList());

        restaurantDTOs.forEach(dto -> log.info("Converted DTO: {}", dto));

        model.addAttribute("restaurantList", restaurantDTOs);

        // 로그인한 사용자의 북마크된 식당 코드 목록 추가 (생략 가능)
        if (principal != null) {
            User user = userService.findByUserId(principal.getName());
            List<Bookmark> bookmarks = bookmarkService.getBookmarksForUser(user);
            Set<Integer> bookmarkedRestaurantCodes = bookmarks.stream()
                    .map(b -> b.getRestaurant().getRestaurantCode())
                    .collect(Collectors.toSet());
            log.info("북마크된 식당 코드: {}", bookmarkedRestaurantCodes);
            model.addAttribute("bookmarkedRestaurantCodes", bookmarkedRestaurantCodes);

            // 3번: 변환된 DTO들을 로그로 확인 (디버깅용)
            restaurantDTOs.forEach(dto -> log.info("Converted DTO restaurantCode: {}", dto.getRestaurantCode()));
            log.info("북마크된 식당 코드: {}", bookmarkedRestaurantCodes);
        }


        return "domain/search/user_restlist";
    }

    @GetMapping("/storeinfo")
    public String restaurantDetail(@RequestParam("restaurantCode") int restaurantCode,
                                   Model model,
                                   Principal principal) {
        Restaurant restaurant = restaurantService.findByRestaurantCode(restaurantCode);
        model.addAttribute("restaurant", restaurant);

        // 북마크 여부 처리 (로그인한 사용자의 경우)
        if (principal != null) {
            User user = userService.findByUserId(principal.getName());
            boolean bookmarked = bookmarkService.isBookmarked(user, restaurant);
            model.addAttribute("bookmarked", bookmarked);
        }

        // 위치 정보 추가 (필요한 경우, restaurant 엔티티나 별도 조회 메서드를 통해 얻어올 수 있음)
        String location = restaurantService.findLocationByRestaurantCode(restaurantCode);
        model.addAttribute("restaurantLocation", location);

        return "domain/restaurant/store_restinfo";
    }
    // 완수 끝
}

//restaurant/ → 식당 관리 (등록, 수정, 조회)

//Controller → 클라이언트 요청을 처리
//Service → 비즈니스 로직을 처리
//Repository → 데이터베이스 CRUD 처리
//DTO → 클라이언트와 데이터를 주고받을 때 사용하는 객체
//Entity → 실제 데이터베이스 테이블과 매칭되는 객체
