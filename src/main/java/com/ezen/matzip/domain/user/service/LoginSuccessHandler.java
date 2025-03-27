package com.ezen.matzip.domain.user.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 권한 문자열을 안전하게 처리
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        String pureRole = role.replace("ROLE_", "");

        // 역할에 따라 리다이렉트 처리 (예시)
        if("USER".equals(pureRole)) {
            response.sendRedirect("/user/main");
        } else if("BUSINESS".equals(pureRole)) {
            response.sendRedirect("/business/main");
        } else if("ADMIN".equals(pureRole)) {
            response.sendRedirect("/admin/main");
        } else {
            response.sendRedirect("/");
        }

//        String role = authentication.getAuthorities().iterator().next().getAuthority();
//        String redirectURL;
//
//        if (role.startsWith("ROLE_")) {
//            role = role.substring(5);
//        }
//
//        switch (role) {
//            case "USER":
//                redirectURL = "/main/main";
//                break;
//            case "BUSINESS":
//                redirectURL = "/business/main";
//                break;
//            case "ADMIN":
//                redirectURL = "/admin/main";
//                break;
//            default:
//                redirectURL = "/";
//                break;
//        }
//
//        response.sendRedirect(redirectURL);
    }
}
