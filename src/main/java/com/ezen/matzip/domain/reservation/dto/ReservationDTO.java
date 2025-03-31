package com.ezen.matzip.domain.reservation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;

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
}
