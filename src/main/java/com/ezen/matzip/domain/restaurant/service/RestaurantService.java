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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final RestaurantStarKeywordRepository keywordRepository;

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

    public List<RestaurantDTO> findByKeywordOrderByScore(String keyword, Sort sort)
    {
        List<Object[]> foundByMenu = menuRepository.findRestaurantAndScoreByMenuName(keyword);
        List<Object[]> foundByRestInfo = restaurantRepository.findRestaurantsByKeywordWithScore(keyword);
        List<Object[]> foundByKeyword = keywordRepository.findRestaurantAndScoreByRestaurantKeyword(keyword);

        Set<RestaurantDTO> resultSet = new TreeSet<>(Comparator.comparing(RestaurantDTO::getScore)
                .thenComparing(p -> p.getrestaurant));

        for (Object[] fmenu : foundByMenu)
        {
            Restaurant menu = (Restaurant)fmenu[0];
            int score = (int)fmenu[1];
            resultSet.add(
                    new RestaurantDTO(
                            menu,
                            menuRepository.findByRestaurantCode(menu),
                            keywordRepository.findByRestaurantCode(menu),
                            score
                    )
            );
        }

        for (Object[] frest : foundByRestInfo)
        {
            Restaurant rest = (Restaurant)frest[0];
            int score = (int)frest[1];
            resultSet.add(
                    new RestaurantDTO(
                            rest,
                            menuRepository.findByRestaurantCode(rest),
                            keywordRepository.findByRestaurantCode(rest),
                            score
                    )
            );
        }

        for (Object[] fkeyw : foundByKeyword)
        {
            Restaurant keyw = (Restaurant)fkeyw[0];
            int score = (int)fkeyw[1];
            resultSet.add(
                    new RestaurantDTO(
                            keyw,
                            menuRepository.findByRestaurantCode(keyw),
                            keywordRepository.findByRestaurantCode(keyw),
                            score
                    )
            );
        }

        return null;
    }
}
