package com.ezen.matzip.domain.review.controller;

import com.ezen.matzip.domain.reservation.dto.ReservationDTO;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.dto.ReviewImageDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.entity.ReviewImage;
import com.ezen.matzip.domain.review.repository.ReviewImageRepository;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import com.ezen.matzip.domain.review.service.ReviewService;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ResourceLoader resourceLoader;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    // 로그인한 사용자의 리뷰 목록을 조회해서 보여줌
    @GetMapping("/user/review")
    public String findReviewByPrincipal(Model model, Principal principal) {
        User user = userService.findByUserId(principal.getName());
        int userCode = user.getUserCode();
        List<ReviewDTO> resultReview = reviewService.findReviewByUserCode(userCode);
        model.addAttribute("testReview", resultReview);
        return "domain/review/review_list";
    }

    // js로 리뷰 코드에 맞는 리뷰 이미지 가져옴
    @GetMapping("/user/review/imageList/{reviewCode}")
    @ResponseBody
    public List<ReviewImageDTO> getReviewImages(@PathVariable(required = false) int reviewCode) {
        List<ReviewImage> images = reviewImageRepository.findReviewImagesByReviewCode(reviewCode);
        return images.stream()
                .map(img -> modelMapper.map(img, ReviewImageDTO.class))
                .toList();
    }

    @PostMapping("/user/review/delete/{reviewCode}")
    public String deleteReview(@PathVariable int reviewCode) {
        reviewService.deleteReview(reviewCode);
        return "redirect:/user/review";
    }

    @PostMapping("/user/review/modify")
    public String modifyReview(@ModelAttribute ReviewDTO reviewDTO,
                               @RequestParam("multiFiles") List<MultipartFile> multiFiles,
                               Principal principal) {
        User user = userService.findByUserId(principal.getName());
        int userCode = user.getUserCode();
        reviewDTO.setUserCode(userCode);
        reviewService.modifyReview(reviewDTO, multiFiles);
        return "redirect:/user/review";
    }

    @GetMapping("/user/review/write")
    public String findReservation(Model model, Principal principal) {
        User user = userService.findByUserId(principal.getName());
        int userCode = user.getUserCode();

        List<ReservationDTO> resultReservation = reviewService.findReservationByUserCode(userCode);
        model.addAttribute("reservation", resultReservation);

        List<ReviewDTO> reviews = reviewService.findReviewByUserCode(userCode);

        Map<Integer, ReviewDTO> reviewMap = reviews.stream()
                .collect(Collectors.toMap(
                        ReviewDTO::getReservationCode,
                        review -> review
                ));
        model.addAttribute("reviewMap", reviewMap);

        return "domain/review/review_write";
    }




    @PostMapping("/user/review/save")
    public String saveReview(@ModelAttribute ReviewDTO reviewDTO,
                             @RequestParam(required = false) List<MultipartFile> multiFiles,
                             Principal principal) throws IOException {

        User user = userService.findByUserId(principal.getName());
        reviewDTO.setUserCode(user.getUserCode());

        // 저장 경로 설정
        ClassPathResource resource = new ClassPathResource("static/img/review");
        String filePath = resource.exists() ? resource.getFile().getAbsolutePath() : new File("src/main/resources/static/img/review").getAbsolutePath();
        new File(filePath).mkdirs();

        List<ReviewImageDTO> files = new ArrayList<>();
        List<MultipartFile> safeFiles = (multiFiles != null) ? multiFiles : new ArrayList<>();

        for (int i = 0; i < Math.min(safeFiles.size(), 3); i++) {
            MultipartFile file = safeFiles.get(i);
            if (file.isEmpty()) continue;

            String originFileName = file.getOriginalFilename();
            if (originFileName == null || !originFileName.contains(".")) {
                throw new IllegalArgumentException("잘못된 파일명: " + originFileName);
            }

            String ext = originFileName.substring(originFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext;

            files.add(new ReviewImageDTO("/img/review/" + savedFileName, originFileName, savedFileName));
            file.transferTo(new File(filePath + "/" + savedFileName));
        }

        reviewService.writeReview(reviewDTO, files);
        return "redirect:/user/review";
    }

}
