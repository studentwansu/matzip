package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.UserRepository;
import com.ezen.matzip.domain.user.service.UserUpdateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UpdateUserController {

    @Autowired
    private UserUpdateService userUpdateservice;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/myinfo")
    public String editUserInfo(Model model, Principal principal) {
        User user = userUpdateservice.findByUserId(principal.getName());
        model.addAttribute("user", user);
        return "domain/sign/user_myinfo";
    }

    @PostMapping("/user/myinfo")
    public String updateUserInfo(
            @ModelAttribute("user") @Valid User formUser,
            BindingResult result,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "passwordCheck", required = false) String passwordCheck,
            @RequestParam("categoryCode") String categoryCodeStr,
            @RequestParam(value = "isVegan", required = false) String isVegan,
            Model model,
            Principal principal) {

        User existingUser = userUpdateservice.findByUserId(principal.getName());
        if (existingUser == null) {
            result.reject("user", "사용자 정보를 찾을 수 없습니다.");
            model.addAttribute("user", User.builder().build());
            return "domain/sign/user_myinfo";
        }

        int categoryCode;
        try {
            categoryCode = Integer.parseInt(categoryCodeStr);
        } catch (NumberFormatException e) {
            result.rejectValue("categoryCode", "error.user", "올바른 음식 카테고리를  선택하세요.");
            model.addAttribute("user", existingUser);
            return "domain/sign/user_myinfo";
        }

        int veganValue = ("1".equals(isVegan)) ? 1 : 0;

        if (userRepository.existsByEmailAndUserIdNot(formUser.getEmail(), existingUser.getUserId())) {
            result.rejectValue("email", "error.user", "중복된 이메일 입니다.");
        }
        if (userRepository.existsByPhoneNumberAndUserIdNot(formUser.getPhoneNumber(), existingUser.getUserId())) {
            result.rejectValue("phoneNumber", "error.user", "중복된 전화번호 입니다.");
        }

        existingUser.updateUserInfo(
                formUser.getName(),
                formUser.getEmail(),
                formUser.getPhoneNumber(),
                formUser.getPasswordQuestion(),
                formUser.getPasswordAnswer(),
                categoryCode,
                veganValue
        );

        if (password != null && !password.trim().isEmpty()) {
            if (!password.equals(passwordCheck)) {
                result.rejectValue("password", "error.user", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                model.addAttribute("user", existingUser);
                return "domain/sign/user_myinfo";
            }
            existingUser.updatePassword(userUpdateservice.encodePassword(password));
        }

        if (result.hasErrors()) {
            return "domain/sign/user_myinfo";
        }

        userUpdateservice.updateUser(existingUser);

        return "redirect:/user/myinfo";
    }
}
