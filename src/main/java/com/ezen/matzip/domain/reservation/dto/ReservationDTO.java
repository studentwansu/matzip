package com.ezen.matzip.domain.reservation.dto;

import com.ezen.matzip.domain.restaurant.entity.Restaurant;
import com.ezen.matzip.domain.review.dto.ReviewImageDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class ReservationDTO {

    private int reservationCode;
    private Date reservationDate;
    private Time reservationTime;
    private int reservationPeople;
    private int restaurantCode;
    private int userCode;
    private enum restaurantStatus {
        방문예정, 방문완료
    }
    private String recipe;

    private String restaurantName;

    public void setRestaurantCode(Restaurant restaurant) {
        this.restaurantCode = restaurant.getRestaurantCode();
    }

    public void setRestaurantName(Restaurant restaurant) {
        this.restaurantName = restaurant.getRestaurantName();
    }
}
