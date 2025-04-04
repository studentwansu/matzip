package com.ezen.matzip.domain.bookmark.dto;

import com.ezen.matzip.domain.restaurant.dto.MenuDTO;
import com.ezen.matzip.domain.restaurant.dto.RestaurantKeywordDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantForBookmarkDTO {
    private int restaurantCode;
    private String restaurantName;
    private String mainMenu;
    private String restaurantLocation;
    // 템플릿에서 기대하는 필드명: restaurantMenus
    private List<MenuDTO> restaurantMenus;
    // 템플릿에서 참조하는 키워드 필드
    private List<RestaurantKeywordDTO> restaurantKeywords;
}
