package com.ezen.matzip.domain.header;

import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import com.ezen.matzip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeaderService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public int getRestaurantActiveNum()
    {
        return restaurantRepository.countAllByRestaurantActiveStatus(1);
    }

//    public int getRestaurantActiveNum
}
