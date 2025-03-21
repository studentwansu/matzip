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
}
