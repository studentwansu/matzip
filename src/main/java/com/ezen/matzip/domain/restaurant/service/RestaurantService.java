package com.ezen.matzip.domain.restaurant.service;

import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import com.ezen.matzip.domain.restaurant.entity.Menu;
import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.restaurant.entity.RestaurantStarKeyword;
import com.ezen.matzip.domain.restaurant.repository.MenuRepository;
import com.ezen.matzip.domain.restaurant.repository.RestaurantRepository;
import com.ezen.matzip.domain.restaurant.repository.RestaurantStarKeywordRepository;
import com.sun.source.util.Trees;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final RestaurantStarKeywordRepository keywordRepository;
//    private final ModelMapper modelMapper;


    public List<RestaurantDTO> findByKeyword(String keyword)
    {
        List<Restaurant> foundByMenu = menuRepository.findRestaurantsByMenuName(keyword);
        List<Restaurant> foundByRestInfo = restaurantRepository.findByRestaurantInfo(keyword);
        List<Restaurant> foundByKeyword = keywordRepository.findRestaurantsByRestaurantKeyword(keyword);

        Set<RestaurantDTO> resultSet = new HashSet<>();

        for (Restaurant menu : foundByMenu)
        {
            resultSet.add(
                    new RestaurantDTO(
                            menu,
                            menuRepository.findByRestaurantCode(menu),
                            keywordRepository.findByRestaurantCode(menu)
                    )
            );
        }

        for (Restaurant rest : foundByRestInfo)
        {
            resultSet.add(
                    new RestaurantDTO(
                            rest,
                            menuRepository.findByRestaurantCode(rest),
                            keywordRepository.findByRestaurantCode(rest)
                    )
            );
        }

        for (Restaurant keyw : foundByKeyword)
        {
            resultSet.add(
                    new RestaurantDTO(
                            keyw,
                            menuRepository.findByRestaurantCode(keyw),
                            keywordRepository.findByRestaurantCode(keyw)
                    )
            );
        }


        return new ArrayList<>(resultSet);
    }
}
