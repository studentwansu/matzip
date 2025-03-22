package com.ezen.matzip.domain.restaurant.dto;

import com.ezen.matzip.domain.restaurant.entity.Keyword;
import com.ezen.matzip.domain.restaurant.entity.Menu;
import com.ezen.matzip.domain.restaurant.entity.Review;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    private int restaurantCode;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantContactNumber;
    private String restaurantDescription;
    private String mainMenu;
    private String restaurantStartTime;
    private String restaurantEndTime;
    private String restaurantService;

    private List<Menu> menus;

    private List<Review> reviews;

    private List<Keyword> keywords;

}
