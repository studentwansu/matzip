package com.ezen.matzip.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="restaurant_star_keyword")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantStarKeyword {
    @Id
    private int restaurantKeywordCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_code")
    private Restaurant restaurantCode;
    private String restaurantKeyword;
}
