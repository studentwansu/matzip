package com.ezen.matzip.domain.restaurant.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestaurantDTO {

    private int restaurantCode;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantContact_number;
    private String restaurantDescription;
    private String mainMenu;
    private String restaurantStartTime;
    private String restaurantEndTime;
    private String restaurantService;

}
