package com.ezen.matzip.domain.restaurant.dto;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RestaurantImageDTO {

    private int restaurantCode;
    private int restaurantImageCode;
    private String restaurantImagePath;
    private String restaurantOriginalName;
    private String restaurantSavedName;


    public RestaurantImageDTO( String restaurantImagePath, String restaurantOriginalName, String restaurantSavedName) {
//        this.restaurantImageCode = restaurantImageCode;
        this.restaurantImagePath = restaurantImagePath;
        this.restaurantOriginalName = restaurantOriginalName;
        this.restaurantSavedName = restaurantSavedName;
    }
}
