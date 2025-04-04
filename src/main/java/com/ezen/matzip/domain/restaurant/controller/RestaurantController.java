package com.ezen.matzip.domain.restaurant.controller;

import com.ezen.matzip.domain.restaurant.dto.RegistDTO;
import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import com.ezen.matzip.domain.restaurant.dto.RestaurantImageDTO;
import com.ezen.matzip.domain.restaurant.entity.Category;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.entity.RestaurantImage;
import com.ezen.matzip.domain.restaurant.repository.RestaurantImageRepository;
import com.ezen.matzip.domain.restaurant.service.RestaurantService;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.dto.ReviewImageDTO;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import com.ezen.matzip.domain.review.service.ReviewService;
import com.ezen.matzip.domain.user.service.UserIdCheckService;
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
import java.util.UUID;

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


    @GetMapping("/admin/restaurant/{restaurantCode}")
    public String getRestaurantDetail(@PathVariable int restaurantCode, Model model) {
        RestaurantDTO restaurant = restaurantService.getRestaurantDetail(restaurantCode);
        model.addAttribute("restaurant", restaurant);

        List<ReviewDTO> resultReview = restaurantService.getReviewsByRestaurant(restaurantCode);
        model.addAttribute("reviews", resultReview);
        System.out.println("test: " + resultReview);

        List<RestaurantImage> imgs = restaurantImageRepository.findRestaurantImageByRestaurantCode(restaurantCode);
        List<RestaurantImageDTO> imgDTOs = imgs.stream()
                .map(img -> modelMapper.map(img, RestaurantImageDTO.class))
                .toList();

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
        System.out.println("test: " + resultReview);

        List<RestaurantImage> imgs = restaurantImageRepository.findRestaurantImageByRestaurantCode(restaurantCode);
        List<RestaurantImageDTO> imgDTOs = imgs.stream()
                .map(img -> modelMapper.map(img, RestaurantImageDTO.class))
                .toList();

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
        // RegistDTO에 비즈니스 코드 설정
        registDTO.setBusinessCode(businessCode); // 비즈니스 코드 설정



        Resource resource = resourceLoader.getResource("file:C:/dev/img/restaurant");
        String filePath = null;

        if(!resource.exists())
        {
            String root = "C:/dev/img/restaurant";
            File file = new File(root);
            file.mkdirs(); // 경로가 없다면 위의 root 경로를 생성하는 메소드

            filePath = file.getAbsolutePath();
        }
        else
            filePath = resource.getFile().getAbsolutePath();
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
                files.add(new RestaurantImageDTO( "/img/restaurant/" + savedFileName, originFileName, savedFileName));

                /** 파일 저장 */
                file.transferTo(new File(filePath + "/" + savedFileName));
                savedFiles.add("C:/dev/img/restaurant" + savedFileName);

                count++;
            }

//            model.addAttribute("message", "파일 업로드 성공!");
//            model.addAttribute("imgs", savedFiles);
        } catch (Exception e) {
            for (RestaurantImageDTO file : files)
            {
                new File(filePath + "/" + file.getRestaurantSavedName()).delete();
            }
//            model.addAttribute("message", "파일 업로드 실패!");
        }

        System.out.println("=== DTO 로그 ===");
        System.out.println(registDTO.toString());
     restaurantService.registRestaurant(registDTO,files);

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
    public String findByMyLocation(@RequestParam String keyword, Model model, HttpSession session)
    {
        session.setAttribute("lastKeyword", keyword);
        List<RestaurantDTO> restaurants = restaurantService.findByKeywordOrderByScore(keyword);
        model.addAttribute("restaurantList", restaurants);
        model.addAttribute("myLoc", keyword);
        return "domain/search/user_restlist";
    }

    @GetMapping("/storeinfo")
    public String markingLocation(@RequestParam Integer restaurantCode, Model model)
    {
        String location = restaurantService.findLocationByRestaurantCode(restaurantCode);
        model.addAttribute("restaurantLocation", location);
        return "/domain/restaurant/store_restinfo";
    }

    @GetMapping(value = "/search", params = "categoryCode")
    public String filteringRestaurants(@RequestParam int categoryCode, Model model, HttpSession session)
    {
        String keyword = (String) session.getAttribute("lastKeyword");
        List<RestaurantDTO> restaurants = restaurantService.filteredRestaurantsByCategory(keyword, categoryCode);
        model.addAttribute("restaurantList", restaurants);
        return "domain/search/user_restlist";
    }

}

//restaurant/ → 식당 관리 (등록, 수정, 조회)

//Controller → 클라이언트 요청을 처리
//Service → 비즈니스 로직을 처리
//Repository → 데이터베이스 CRUD 처리
//DTO → 클라이언트와 데이터를 주고받을 때 사용하는 객체
//Entity → 실제 데이터베이스 테이블과 매칭되는 객체
