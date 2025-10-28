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
        // 1. 일반 사용자(User) 테이블에서 먼저 찾아봅니다.
        User user = userRepository.findByUserId(username).orElse(null);

        if (user != null) {
            // --- ★★★ 일반 사용자 계정 상태 확인 로직 추가 ★★★ ---
            // account_status가 1이면 '정지' 상태로 가정합니다.
            if (user.getAccountStatus() != null && user.getAccountStatus() == 1) {
                throw new LockedException("관리자에 의해 이용이 제한된 계정입니다.");
            }
            // --- ★★★ 추가 로직 끝 ★★★ ---
            return new CustomUserDetails(user);
        }

        // 2. 일반 사용자가 없다면, 사업자(Business) 테이블에서 찾아봅니다.
        Business business = businessRepository.findByUserId(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 아이디를 찾을 수 없습니다: " + username));

        // --- ★★★ 사업자 계정 상태 확인 로직 추가 ★★★ ---
        // Business 엔티티에 계정 상태 컬럼이 있다면, 동일하게 확인 로직을 추가해야 합니다.
        // 만약 Business 엔티티에 'accountStatus' 같은 필드가 없다면 이 부분은 생략해도 됩니다.
        // if (business.getAccountStatus() != null && business.getAccountStatus() == 1) {
        //     throw new LockedException("관리자에 의해 이용이 제한된 계정입니다.");
        // }
        // --- ★★★ 추가 로직 끝 ★★★ ---

        return new CustomUserDetails(business);
    }
}
