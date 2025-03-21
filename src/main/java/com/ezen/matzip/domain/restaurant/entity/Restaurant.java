package com.ezen.matzip.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_code")
    private int restaurantCode;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantContactNumber;
    private String restaurantDescription;
    private String mainMenu;
    private String restaurantStartTime;
    private String restaurantEndTime;
    private String restaurantService;

    @OneToMany
    @JoinColumn(name="restaurantCode")
//    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Menu> menus;

    @OneToMany
    @JoinColumn(name="restaurantCode")
    private List<Review> reviews;

}
