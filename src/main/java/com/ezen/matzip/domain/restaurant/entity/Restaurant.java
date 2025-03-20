package com.ezen.matzip.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="restaurant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Restaurant {
    @Id
    private int restaurantCode;
    private String restaurantName;
    private String restaurantLocation;
    private String restaurantContactNumber;
    private String restaurantDescription;
    private Date restaurantRegistrationDate;
    private int restaurantActiveStatus;
    private String restaurantUniqueKeywords;
    private String mainMenu;
    private int businessCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private Category category;
    private Time restaurantStartTime;
    private Time restaurantEndTime;
    private int restaurantStatus;
    private String restaurantService;
}
