package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.user.dto.UserRequestDTO;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.UserRepository;
import com.ezen.matzip.exception.DuplicateUserIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DuplicateChecker duplicateChecker;

    public void registerUser(UserRequestDTO urdto) {
        if (duplicateChecker.isDuplicatedId(urdto.getId())) {
            throw new DuplicateUserIdException("이미 존재하는 아이디입니다.");
        }

        User user = new User(urdto.getUserName(), urdto.getId(), urdto.getPassword(), urdto.getQuestion(), urdto.getAnswer(),
                urdto.getEmail(), urdto.getPhone(), urdto.getCountry(), urdto.getRole(), urdto.getPreference(), urdto.getVegan());
        userRepository.save(user);
    }
}


