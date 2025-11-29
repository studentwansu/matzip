package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.BusinessRepository;
import com.ezen.matzip.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, BusinessRepository businessRepository) {
        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username).orElse(null);

        if (user != null) {
            if (user.getAccountStatus() != null && user.getAccountStatus() == 1) {
                throw new LockedException("관리자에 의해 이용이 제한된 계정입니다.");
            }
            return new CustomUserDetails(user);
        }

        Business business = businessRepository.findByUserId(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 아이디를 찾을 수 없습니다: " + username));

        return new CustomUserDetails(business);
    }
}
