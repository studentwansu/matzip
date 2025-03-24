package com.ezen.matzip.domain.restaurant.service;

import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.repository.MenuRepository;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.restaurant.repository.KeywordRepository;
import com.ezen.matzip.domain.review.dto.ReviewDTO;
import com.ezen.matzip.domain.review.entity.Review;
import com.ezen.matzip.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final KeywordRepository keywordRepository;
    private final ReviewRepository reviewRepository;

    public List<ReviewDTO> getReviewsByRestaurant(int restaurantCode)
    {
        Restaurant restaurant = restaurantRepository.findByRestaurantCode(restaurantCode);
        List<Review> reviews = reviewRepository.findByRestaurantCode(restaurant);
        List<ReviewDTO> result = new ArrayList<>();
        for (Review review : reviews)
        {
            ReviewDTO dto = new ReviewDTO();
            dto.setRestaurantName(restaurant);
            dto.setRestaurantCode(restaurant);
            dto.setReviewCode(review.getReviewCode());
            dto.setReviewDate(review.getReviewDate());
            dto.setReviewContent(review.getReviewContent());
            dto.setRating(review.getRating());

            result.add(dto);
        }

        return result;
    }

    public RestaurantDTO getRestaurantDetail(int restaurantCode) {
        Restaurant restaurant = restaurantRepository.findByRestaurantCode(restaurantCode);
        System.out.println("리뷰 : " + restaurant.getKeywords());
        return new RestaurantDTO(
                restaurant,
                menuRepository.findByRestaurantCode(restaurant),
                keywordRepository.findByRestaurantCode(restaurant)
        );
    }

    public String[] splitKeywords(String keyword)
    {
        String[] keywords = keyword.split("\\s");
        return keywords;
    }

    public List<RestaurantDTO> findByKeywordOrderByScore(String keyword)
    {
        List<Object[]> foundByMenu = menuRepository.findRestaurantAndScoreByMenuName(keyword);
        List<Object[]> foundByRestInfo = restaurantRepository.findRestaurantsByKeywordWithScore(keyword);
        List<Object[]> foundByKeyword = keywordRepository.findRestaurantAndScoreByRestaurantKeyword(keyword);

        Map<RestaurantDTO, Integer> resultSet = new HashMap<>();

        for (Object[] fmenu : foundByMenu)
        {
            Restaurant menu = (Restaurant)fmenu[0];
            Integer score = ((Number) fmenu[1]).intValue();

            resultSet.put(
                    new RestaurantDTO(
                            menu,
                            menuRepository.findByRestaurantCode(menu),
                            keywordRepository.findByRestaurantCode(menu)
                    ), score
            );
        }

        for (Object[] frest : foundByRestInfo)
        {
            Restaurant rest = (Restaurant)frest[0];
            Integer score = ((Number) frest[1]).intValue();
            RestaurantDTO dto = new RestaurantDTO(rest,
                    menuRepository.findByRestaurantCode(rest),
                    keywordRepository.findByRestaurantCode(rest));

            if(resultSet.containsKey(dto))
            {
                Integer newScore = resultSet.get(dto) + score;
                resultSet.put(dto, newScore);
            }
            else
                resultSet.put(dto, score);
        }

        for (Object[] fkeyw : foundByKeyword)
        {
            Restaurant rest = (Restaurant)fkeyw[0];
            Integer score = ((Number) fkeyw[1]).intValue();
            RestaurantDTO dto = new RestaurantDTO(rest,
                    menuRepository.findByRestaurantCode(rest),
                    keywordRepository.findByRestaurantCode(rest));

            if(resultSet.containsKey(dto))
            {
                Integer newScore = resultSet.get(dto) + score;
                resultSet.put(dto, newScore);
            }
            else
                resultSet.put(dto, score);
        }

        resultSet.forEach((key, value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });

        List<Map.Entry<RestaurantDTO, Integer>> sortedList = new ArrayList<>(resultSet.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<RestaurantDTO> finalList = new ArrayList<>();
        for (Map.Entry<RestaurantDTO, Integer> restaurant : sortedList)
        {
            finalList.add(restaurant.getKey());
            System.out.println("final: " + restaurant.getKey());
        }

        return finalList;
    }

    public String findLocationByRestaurantCode(Integer restaurantCode)
    {
        Restaurant restarant = restaurantRepository.findByRestaurantCode(restaurantCode);
        System.out.println("location: " + restarant.getRestaurantLocation());
        return restarant.getRestaurantLocation();
    }
}
