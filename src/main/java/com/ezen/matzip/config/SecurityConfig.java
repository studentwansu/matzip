package com.ezen.matzip.config;

import com.ezen.matzip.domain.user.service.CustomAuthFailureHandler;
import com.ezen.matzip.domain.user.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final CustomAuthFailureHandler customAuthFailureHandler;

public SecurityConfig(LoginSuccessHandler loginSuccessHandler, CustomAuthFailureHandler customAuthFailureHandler) {
    this.loginSuccessHandler = loginSuccessHandler;
    this.customAuthFailureHandler = customAuthFailureHandler;
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/main/main", "/login", "/signup", "/signup/**", "/css/**", "/js/**", "/img/**", "/html/**", "/checkUserId",
                                "/fragments/**", "/search", "/search/**", "/storeinfo/**", "/minigame/**", "/weather/**","/restaurant/**",
                                "/findpwd", "/checkUserInfo", "/resetPassword","/board/**", "/error/**" ).permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/business/**").hasRole("BUSINESS")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(customAuthFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/user/main")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionFixation().newSession()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .csrf(withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**", "/dapi/**", "/html/**", "/checkUserId", "/business/review/report", "/search/**", "/js/**", "/img/**"));

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

