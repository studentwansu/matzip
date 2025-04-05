package com.ezen.matzip.domain.restaurant.controller;

import com.ezen.matzip.domain.bookmark.dto.RestaurantForBookmarkDTO;
import com.ezen.matzip.domain.bookmark.entity.Bookmark;
import com.ezen.matzip.domain.bookmark.service.BookmarkService;
import com.ezen.matzip.domain.restaurant.dto.RegistDTO;
import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import com.ezen.matzip.domain.restaurant.dto.RestaurantImageDTO;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.entity.RestaurantImage;
import com.ezen.matzip.domain.restaurant.repository.RestaurantImageRepository;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.restaurant.service.RestaurantService;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.user.dto.UserRequestDTO;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.service.UserService;
import com.ezen.matzip.domain.user.service.UserIdCheckService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class RestaurantController {

    @Autowired
    private final RestaurantService restaurantService;
    @Autowired
    private RestaurantImageRepository restaurantImageRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private UserIdCheckService userIdCheckService;
//    private final ReviewService reviewService;

    //완수 북마크 기능에 필요
    @Autowired
    private final BookmarkService bookmarkService;
    @Autowired
    private final UserService userService;
    @Autowired
    private RestaurantRepository restaurantRepository;


    @GetMapping("/restaurant/{restaurantCode}")
    public String getRestaurantForUsers(@PathVariable int restaurantCode, Model model, HttpServletRequest request, Principal principal) {
        RestaurantDTO restaurant = restaurantService.getRestaurantDetail(restaurantCode);
        model.addAttribute("restaurant", restaurant);

        // 완수-현재 요청 URL 추가
        model.addAttribute("currentUri", request.getRequestURI());

        List<ReviewDTO> resultReview = restaurantService.getReviewsByRestaurant(restaurantCode);
        model.addAttribute("reviews", resultReview);
        List<RestaurantImage> imgs = restaurantImageRepository.findRestaurantImageByRestaurantCode(restaurantCode);
        // 완수-사용자가 로그인한 경우 북마크 여부 확인 후 모델에 추가
        // 로그인한 사용자라면 북마크 여부 체크
        if (principal != null) {
            User user = userService.findByUserId(principal.getName());
            // 북마크 여부를 판단할 때, findByUserAndRestaurant로 조회해 보세요.
            boolean bookmarked = bookmarkService.findByUserAndRestaurant(user, restaurantService.findByRestaurantCode(restaurantCode)).isPresent();
            model.addAttribute("bookmarked", bookmarked);
        } else {
            // 로그인하지 않은 경우 false로 처리
            model.addAttribute("bookmarked", false);
        }
//        System.out.println("reviews: " + resultReview);

//        List<RestaurantImage> imgs = restaurantImageRepository.findRestaurantImageByRestaurantCode(restaurantCode);
//        if (!imgs.isEmpty())
//        {
//
//            List<RestaurantImageDTO> imgDTOs = imgs.stream()
//                .map(img -> modelMapper.map(img, RestaurantImageDTO.class))
//                .toList();
//            model.addAttribute("selectedRestaurantImgs", imgDTOs);
//        }
            List<RestaurantImageDTO> imgDTOs = imgs.stream()
                .map(img -> modelMapper.map(img, RestaurantImageDTO.class))
                .toList();
            model.addAttribute("selectedRestaurantImgs", imgDTOs);

        model.addAttribute("selectedRestaurant", restaurant);

        // 완수-현재 URL을 모델에 추가
        model.addAttribute("currentUri", request.getRequestURI());

        return "domain/restaurant/user_restinfo";
    }

    @GetMapping("/admin/restaurant/{restaurantCode}")
    public String getRestaurantDetail(@PathVariable int restaurantCode, Model model) {
        RestaurantDTO restaurant = restaurantService.getRestaurantDetail(restaurantCode);
        model.addAttribute("restaurant", restaurant);

        List<ReviewDTO> resultReview = restaurantService.getReviewsByRestaurant(restaurantCode);
        model.addAttribute("reviews", resultReview);
        System.out.println("test: " + resultReview);

        List<RestaurantImage> imgs = restaurantImageRepository.findRestaurantImageByRestaurantCode(restaurantCode);
        List<RestaurantImageDTO> imgDTOs = imgs.stream().map(img -> modelMapper.map(img, RestaurantImageDTO.class)).toList();

        model.addAttribute("selectedRestaurant", modelMapper.map(restaurant, RestaurantDTO.class));
        model.addAttribute("selectedRestaurantImgs", imgDTOs);

        return "domain/restaurant/admin_restinfo";
    }

    @GetMapping("/business/restaurant/{restaurantCode}")
    public String getRestaurantDetail2(@PathVariable int restaurantCode, Model model) {
        RestaurantDTO restaurant = restaurantService.getRestaurantDetail(restaurantCode);
        model.addAttribute("restaurant", restaurant);

        List<ReviewDTO> resultReview = restaurantService.getReviewsByRestaurant(restaurantCode);
        model.addAttribute("reviews", resultReview);

        List<RestaurantImage> imgs = restaurantImageRepository.findRestaurantImageByRestaurantCode(restaurantCode);
        List<RestaurantImageDTO> imgDTOs = imgs.stream().map(img -> modelMapper.map(img, RestaurantImageDTO.class)).toList();

        model.addAttribute("selectedRestaurant", modelMapper.map(restaurant, RestaurantDTO.class));
        model.addAttribute("selectedRestaurantImgs", imgDTOs);

        return "domain/restaurant/store_restinfo";
    }

    @GetMapping("/business/regist")
    public String registPage(Principal principal) {
        String username = principal.getName();
        int businessCode = userIdCheckService.getBusinessCodeByUserid(username);



        Model model = new ExtendedModelMap();
        model.addAttribute("businessCode", businessCode);

//        return "restaurant/restaurant_regist";
        return "domain/store/store_apply";
    }

    @PostMapping("/business/regist")
    public String regist(@ModelAttribute RegistDTO registDTO,
                         @RequestParam List<MultipartFile> multiFiles, Principal principal) throws IOException {

        // Principal에서 현재 로그인된 사용자 이름 가져오기
        String username = principal.getName();
        // userService에서 username을 사용하여 businessCode를 가져오기
        Integer businessCode = userIdCheckService.getBusinessCodeByUserid(username);

        Restaurant existingRestaurant = restaurantRepository.findByBusinessCode(businessCode);
        if (existingRestaurant != null) {
            // 레스토랑이 이미 등록되어 있으면 등록을 막고 경고 메시지를 반환
            throw new RuntimeException("이미 식당을 등록 하셨습니다.");
        }

        // RegistDTO에 비즈니스 코드 설정
        registDTO.setBusinessCode(businessCode); // 비즈니스 코드 설정
      
        Resource resource = resourceLoader.getResource("file:C:/dev/img/restaurant");
        String filePath = null;

        if (!resource.exists()) {
            String root = "C:/dev/img/restaurant";
            File file = new File(root);
            file.mkdirs(); // 경로가 없다면 위의 root 경로를 생성하는 메소드

            filePath = file.getAbsolutePath();
        } else filePath = resource.getFile().getAbsolutePath();
        System.out.println("filePath: " + filePath);
        /** 파일에 관한 정보 저장을 위한 처리 */
        List<RestaurantImageDTO> files = new ArrayList<>(); // 파일에 관한 정보 저장할 리스트
        List<String> savedFiles = new ArrayList<>();

        try {
            int count = 0;
            for (MultipartFile file : multiFiles) {
                if (count >= 3) break;
                /** 파일명 변경 처리 */
//                int restaurantCode = Integer.parseInt(file.getOriginalFilename());
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext;

                /** 파일정보 등록 */
                files.add(new RestaurantImageDTO("/img/restaurant/" + savedFileName, originFileName, savedFileName));

                /** 파일 저장 */
                file.transferTo(new File(filePath + "/" + savedFileName));
                savedFiles.add("C:/dev/img/restaurant" + savedFileName);

                count++;
            }

//            model.addAttribute("message", "파일 업로드 성공!");
//            model.addAttribute("imgs", savedFiles);
        } catch (Exception e) {
            for (RestaurantImageDTO file : files) {
                new File(filePath + "/" + file.getRestaurantSavedName()).delete();
            }
//            model.addAttribute("message", "파일 업로드 실패!");

        }
        System.out.println("=== DTO 로그 ===");
        System.out.println(registDTO.toString());
        int restaurantCode = restaurantService.registRestaurant(registDTO,files).getRestaurantCode();


        return "redirect:/business/restaurant/" + restaurantCode;
    }

    @GetMapping("/business/modify")
    public String modifyPage(Principal principal) {
        String username = principal.getName();
        int businessCode = userIdCheckService.getBusinessCodeByUserid(username);

        Model model = new ExtendedModelMap();
        model.addAttribute("businessCode", businessCode);
        return "domain/store/store_edit";
    }

    @PostMapping("/business/modify")
    public String modify(@ModelAttribute RegistDTO registDTO,
                         @RequestParam ("multiFiles")List<MultipartFile> multiFiles, Principal principal) {

        Business business = userIdCheckService.findByUserId(principal.getName());
        int businessCode = business.getBusinessCode();
        registDTO.setBusinessCode(businessCode);

        Restaurant foundRestaurant = restaurantRepository.findByBusinessCode(businessCode);
        registDTO.setRestaurantCode(foundRestaurant.getRestaurantCode());

//        // Principal에서 현재 로그인된 사용자 이름 가져오기
//        String username = principal.getName();
//        // userService에서 username을 사용하여 businessCode를 가져오기
//        Integer businessCode = userIdCheckService.getBusinessCodeByUserid(username);
//        // RegistDTO에 비즈니스 코드 설정
//        registDTO.setBusinessCode(businessCode); // 비즈니스 코드 설정

        System.out.println("businessCode: " + businessCode);
        System.out.println("modify: " + registDTO.toString());
        restaurantService.modifyRestaurant(registDTO, multiFiles);

        return "redirect:/restaurant/" + registDTO.getRestaurantCode();
    }

    @GetMapping("/search")
    public String findByRestaurant(@RequestParam String keyword, Model model, HttpSession session, Principal principal) {
        session.setAttribute("lastKeyword", keyword);
        List<RestaurantDTO> restaurants = restaurantService.findByKeywordOrderByScore(keyword);
        model.addAttribute("restaurantList", restaurants);
        model.addAttribute("myLoc", keyword);

        //완수- 북마크 기능에 필요
        if (principal != null) {
            User user = userService.findByUserId(principal.getName());
            List<Bookmark> bookmarks = bookmarkService.getBookmarksForUser(user);
            Set<Integer> bookmarkedRestaurantCodes = bookmarks.stream().map(b -> b.getRestaurant().getRestaurantCode()).collect(Collectors.toSet());
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
    public String filteringRestaurants(@RequestParam int categoryCode, Model model, HttpSession session) {
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
        List<RestaurantForBookmarkDTO> restaurantDTOs = restaurantEntities.stream().map(restaurant -> restaurantService.convertToRestaurantForBookmarkDTO(restaurant)).collect(Collectors.toList());

        restaurantDTOs.forEach(dto -> log.info("Converted DTO: {}", dto));

        model.addAttribute("restaurantList", restaurantDTOs);

        // 로그인한 사용자의 북마크된 식당 코드 목록 추가 (생략 가능)
        if (principal != null) {
            User user = userService.findByUserId(principal.getName());
            List<Bookmark> bookmarks = bookmarkService.getBookmarksForUser(user);
            Set<Integer> bookmarkedRestaurantCodes = bookmarks.stream().map(b -> b.getRestaurant().getRestaurantCode()).collect(Collectors.toSet());
            log.info("북마크된 식당 코드: {}", bookmarkedRestaurantCodes);
            model.addAttribute("bookmarkedRestaurantCodes", bookmarkedRestaurantCodes);

            // 3번: 변환된 DTO들을 로그로 확인 (디버깅용)
            restaurantDTOs.forEach(dto -> log.info("Converted DTO restaurantCode: {}", dto.getRestaurantCode()));
            log.info("북마크된 식당 코드: {}", bookmarkedRestaurantCodes);
        }


        return "domain/search/user_restlist";
    }

    @GetMapping("/restaurant/storeinfo")
    public String restaurantDetail(@RequestParam("restaurantCode") int restaurantCode, Model model, Principal principal) {

        // 엔티티 대신 DTO 사용
        RestaurantDTO restaurantDTO = restaurantService.getRestaurantDetail(restaurantCode);
        model.addAttribute("restaurant", restaurantDTO);

//        Restaurant restaurant = restaurantService.findByRestaurantCode(restaurantCode);
//        model.addAttribute("restaurant", restaurant);

        // 로그인한 사용자의 북마크 여부 처리
//        if (principal != null) {
//            User user = userService.findByUserId(principal.getName());
//            boolean bookmarked = bookmarkService.isBookmarked(user, restaurantDTO.convertToEntity());
//            model.addAttribute("bookmarked", bookmarked);
//        }

        // 엔티티를 별도로 조회해서 북마크 여부 확인
        Restaurant restaurant = restaurantService.findByRestaurantCode(restaurantCode);
        if (principal != null) {
            User user = userService.findByUserId(principal.getName());
            boolean bookmarked = bookmarkService.isBookmarked(user, restaurant);
            model.addAttribute("bookmarked", bookmarked);
        }
//        // 북마크 여부 처리 (로그인한 사용자의 경우)
//        if (principal != null) {
//            User user = userService.findByUserId(principal.getName());
//            boolean bookmarked = bookmarkService.isBookmarked(user, restaurant);
//            model.addAttribute("bookmarked", bookmarked);
//        }

        // 위치 정보 추가 (필요한 경우, restaurant 엔티티나 별도 조회 메서드를 통해 얻어올 수 있음)
        String location = restaurantService.findLocationByRestaurantCode(restaurantCode);
        model.addAttribute("restaurantLocation", location);

        return "domain/restaurant/user_restinfo";
    }
    // 완수 끝
}

//restaurant/ → 식당 관리 (등록, 수정, 조회)

//Controller → 클라이언트 요청을 처리
//Service → 비즈니스 로직을 처리
//Repository → 데이터베이스 CRUD 처리
//DTO → 클라이언트와 데이터를 주고받을 때 사용하는 객체
//Entity → 실제 데이터베이스 테이블과 매칭되는 객체
