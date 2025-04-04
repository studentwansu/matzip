package com.ezen.matzip.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantImageCode;
    @ManyToOne
    @JoinColumn(name = "restaurant_code",nullable = false)
    private Restaurant restaurantCode;
    private String restaurantImagePath;
    private String restaurantOriginalName;
    private String restaurantSavedName;

    @Builder
    public RestaurantImage(Restaurant restaurant,String restaurantImagePath, String restaurantOriginalName, String restaurantSavedName){
        this.restaurantCode = restaurant;
        this.restaurantImagePath = restaurantImagePath;
        this.restaurantOriginalName = restaurantOriginalName;
        this.restaurantSavedName = restaurantSavedName;
    }


}
