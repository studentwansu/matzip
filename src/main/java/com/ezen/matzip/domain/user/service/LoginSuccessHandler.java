package com.ezen.matzip.domain.user.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String pureRole = role.replace("ROLE_", "");

        System.out.println("로그인 성공 후 역할: " + role);
        System.out.println("로그인 성공 후 pureRole: " + pureRole);

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

