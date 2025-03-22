package com.ezen.matzip.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "restaurant_star_keyword")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_keyword_code")

    private int restaurantKeywordCode;
    private int restaurantCode;
    private String restaurantKeyword;
}
