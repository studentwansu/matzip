package com.ezen.matzip.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantCode;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantContactNumber;
    private String restaurantDescription;
    private String mainMenu;
    private String restaurantStartTime;
    private String restaurantEndTime;
    private String restaurantService;

}
