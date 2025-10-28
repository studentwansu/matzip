package com.ezen.matzip.domain.user.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component // 이 클래스를 스프링 빈으로 등록합니다.
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;

        // --- ★★★ 수정된 부분: 예외 확인 순서 변경 및 원인(cause) 확인 로직 추가 ★★★ ---

        // 1. 가장 먼저, 예외의 근본 원인(cause)이 LockedException인지 확인합니다.
        if (exception.getCause() instanceof LockedException) {
            errorMessage = exception.getMessage(); // "관리자에 의해..." 메시지를 그대로 사용

            // 2. 근본 원인이 없다면, 겉에 있는 예외 자체가 LockedException인지 확인합니다.
        } else if (exception instanceof LockedException) {
            errorMessage = exception.getMessage();

            // 3. 둘 다 아니라면, 아이디/비밀번호 오류로 간주합니다.
        } else if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다.";

            // 4. 그 외 알 수 없는 오류
        } else {
            errorMessage = "로그인 과정에서 알 수 없는 오류가 발생했습니다.";
        }
        // --- ★★★ 수정 끝 ★★★ ---

        errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
        setDefaultFailureUrl("/login?error=true&message=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
