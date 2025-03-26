package com.ezen.matzip.domain.restaurant.entity;

import com.ezen.matzip.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
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
    private Time restaurantStartTime;
    private Time restaurantEndTime;
    private String restaurantService;
    private Date restaurantRegistrationDate;
    private int restaurantActiveStatus;
    private String restaurantUniqueKeywords;
    private int businessCode;
    private int restaurantStatus;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;


    @OneToMany
    @JoinColumn(name="restaurant_code")
    private List<Menu> menus;

    @OneToMany
    @JoinColumn(name="restaurant_code")
    private List<Review> reviews;

    @OneToMany
    @JoinColumn(name="restaurant_code")
    private List<Keyword> keywords;


    @Builder
    public Restaurant(int restaurantCode, String restaurantName, int businessCode) {
        this.restaurantCode = restaurantCode;
        this.restaurantName = restaurantName;
        this.businessCode = businessCode;
    }
}
