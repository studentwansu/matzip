package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.user.dto.BusinessRequestDTO;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private DuplicateChecker duplicateChecker;

    public void registerBusiness(BusinessRequestDTO bsdto) {
        if (duplicateChecker.isDuplicatedId(bsdto.getId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        Business business = new Business(bsdto.getRestaurantName(), bsdto.getId(), bsdto.getPassword(), bsdto.getQuestion(),
                bsdto.getAnswer(), bsdto.getEmail(), bsdto.getPhone(), bsdto.getRole(), bsdto.getRestaurantNumber());
        businessRepository.save(business);
    }
}


