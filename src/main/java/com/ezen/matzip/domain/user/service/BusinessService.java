package com.ezen.matzip.domain.user.service;

import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.repository.MenuRepository;
import com.ezen.matzip.domain.restaurant.repository.RestaurantImageRepository;
import com.ezen.matzip.domain.restaurant.repository.RestaurantKeywordRepository;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.entity.User;
import com.ezen.matzip.domain.user.repository.BusinessRepository;
import com.ezen.matzip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final RestaurantKeywordRepository restaurantKeywordRepository;
    private final RestaurantImageRepository restaurantImageRepository;

//    @Autowired
//    public BusinessService(BusinessRepository businessRepository) {
//        this.businessRepository = businessRepository;
//    }

    public Business findByUserId(String userId) {
        return businessRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));
    }

    public RestaurantDTO findRestaurantByUserId(String userId)
    {
        Business business = businessRepository.findByUserId(userId).orElseThrow();
        Restaurant restaurant = restaurantRepository.findByBusinessCode(business.getBusinessCode());

        return new RestaurantDTO(restaurant,
                menuRepository.findByRestaurantCode(restaurant),
                restaurantKeywordRepository.findByRestaurantCode(restaurant),
                restaurantImageRepository.findRestaurantImageByRestaurantCode(restaurant));
    }


}
