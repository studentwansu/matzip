package com.ezen.matzip.config;

import com.ezen.matzip.domain.user.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;

    // 생성자 주입으로 LoginSuccessHandler 받기
    public SecurityConfig(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        // [1] 공지사항(Notice) 세부 권한 설정 (구체적인 경로부터)
                        // 1-1) 공지사항 목록, 상세보기(주로 GET)는 모두에게 허용
                        .requestMatchers(HttpMethod.GET,
                                "/board/notice",          // 목록
                                "/board/notice/",         // 목록 (슬래시 포함)
                                "/board/notice/{id}",     // 상세
                                "/board/notice/detail/**" // 상세(또는 기타 GET)
                        ).permitAll()

                        // 1-2) 공지사항 작성/수정/삭제(주로 POST)는 관리자만 접근
                        .requestMatchers(HttpMethod.POST,
                                "/board/notice/create",
                                "/board/notice/edit/**",
                                "/board/notice/delete/**",
                                "/board/notice/write"
                        ).hasRole("ADMIN")

                        // 만약 작성 폼, 수정 폼( GET ) 자체도 관리자만 열람하게 하려면:
                        .requestMatchers(HttpMethod.GET,
                                "/board/notice/create",
                                "/board/notice/edit/**",
                                "/board/notice/write"
                        ).hasRole("ADMIN")

                        // [2] 그 외 나머지 경로들
                        .requestMatchers("/", "/main/**", "/login", "/signup", "/signup/**",
                                "/css/**", "/js/**", "/img/**", "/html/**", "/board/notice/**")
                        .permitAll()

                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/business/**").hasRole("BUSINESS")
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 기존에 있던 /board/**, /notice/** 전부 허용은
                        // 공지사항에 대한 세부 설정과 충돌될 수 있으므로 제거하거나
                        // 더 아래에 배치해서 "notice" 경로가 먼저 매칭되도록 조정합니다.
                        // .requestMatchers("/board/**", "/notice/**").permitAll()

                        // [3] 테스트용 anyRequest().permitAll() → 실제 운영 시 수정 필요
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionFixation().newSession()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .csrf(withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/main/main", "/login", "/signup", "/signup/**", "/css/**", "/js/**", "/img/**").permitAll()
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .requestMatchers("/business/**").hasRole("BUSINESS")
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
////                        .anyRequest().authenticated()
//                                .anyRequest().permitAll()
//                )
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        // 로그인 폼에서 "userId", "password"라는 필드명을 사용한다면:
//                        .usernameParameter("userId")
//                        .passwordParameter("password")
//                        .successHandler(new LoginSuccessHandler())
//                        // 로그인 실패 시 에러 파라미터를 붙여 리다이렉트 (옵션)
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .permitAll()
//                )
//                .sessionManagement(session -> session
//                        .sessionFixation().newSession()
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(false)
//                )
//                .csrf(withDefaults());
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}


