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
        String redirectURL;

        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }

        switch (role) {
            case "USER":
                redirectURL = "/main/main";
                break;
            case "BUSINESS":
                redirectURL = "/business/main";
                break;
            case "ADMIN":
                redirectURL = "/admin/main";
                break;
            default:
                redirectURL = "/";
                break;
        }

        response.sendRedirect(redirectURL);
    }
}
