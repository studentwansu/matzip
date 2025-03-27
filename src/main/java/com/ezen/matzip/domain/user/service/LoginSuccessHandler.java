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

        // 단일 권한만 사용한다고 가정하고 첫 번째 권한을 가져옵니다.
        String role = authentication.getAuthorities().iterator().next().getAuthority();
// "ROLE_" 접두사가 있다면 제거합니다.
        String pureRole = role.replace("ROLE_", "");

        System.out.println("로그인 성공 후 역할: " + role); // 예: "ROLE_ADMIN"
        System.out.println("로그인 성공 후 pureRole: " + pureRole); // 예: "ADMIN"

        switch (pureRole) {
            case "USER":
                response.sendRedirect("/user/main");
                break;
            case "BUSINESS":
                response.sendRedirect("/business/main");
                break;
            case "ADMIN":
                response.sendRedirect("/admin/main");
                break;
            default:
                response.sendRedirect("/");
                break;
        }
    }
}

