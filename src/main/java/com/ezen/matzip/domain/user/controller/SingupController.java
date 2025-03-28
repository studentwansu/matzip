package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.user.dto.BusinessRequestDTO;
import com.ezen.matzip.domain.user.dto.UserRequestDTO;
import com.ezen.matzip.domain.user.service.BusinessRegistrationService;
import com.ezen.matzip.domain.user.service.UserRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SingupController {

    private final UserRegistrationService userRegistrationService;
    private final BusinessRegistrationService businessRegistrationService;

    @Autowired
    public SingupController(UserRegistrationService userRegistrationService, BusinessRegistrationService businessRegistrationService) {
        this.userRegistrationService = userRegistrationService;
        this.businessRegistrationService = businessRegistrationService;
    }

    @GetMapping("/signup")
    public String showSignupChoice() {
        return "common/signup";
    }

    @GetMapping("/signup/user")
    public String showUserSignupForm(Model model) {
        model.addAttribute("userRequestDTO", new UserRequestDTO());
        return "domain/sign/user_signup";
    }

    @PostMapping("/signup/user")
    public String registerUser(
            @Valid @ModelAttribute("userRequestDTO") UserRequestDTO userRequestDTO,
            BindingResult bindingResult,
            Model model) {

        System.out.println("userRequestDTO : " + userRequestDTO);

        // 유효성 검사 오류 처리
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("errorMessage", "회원가입 양식을 올바르게 작성해주세요.");
//            return "domain/sign/user_signup";
//        }
//
//        try {
            // 디버깅 로그 추가
            System.out.println("🚀 회원가입 요청 받음: " + userRequestDTO.getUserId());

            userRegistrationService.register(userRequestDTO);
            System.out.println("✅ 회원가입 성공: " + userRequestDTO.getUserId());

            model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다.");
            return "redirect:/login";  // 로그인 페이지로 리다이렉트
//
//        } catch (RuntimeException e) {
//            System.err.println("❌ 회원가입 실패: " + e.getMessage());
//            model.addAttribute("errorMessage", "회원가입에 실패했습니다: " + e.getMessage());
//            return "domain/sign/user_signup";
//        }
    }

    @GetMapping("/signup/business")
    public String showBusinessSignupForm(Model model) {
        model.addAttribute("businessRequestDTO", new BusinessRequestDTO());
        return "domain/sign/store_signup";
    }

    @PostMapping("/signup/business")
    public String registerBusiness(@Valid @ModelAttribute("businessRequestDTO") BusinessRequestDTO businessRequestDTO,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 메시지를 콘솔에 출력
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "domain/sign/store_signup";
        }
        try {
            businessRegistrationService.register(businessRequestDTO);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "domain/sign/store_signup";
        }
        // 회원가입 성공 후 로그인 페이지로 리다이렉트
        return "redirect:/login";
    }
}
