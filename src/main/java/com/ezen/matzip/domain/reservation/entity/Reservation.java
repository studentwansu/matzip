package com.ezen.matzip.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
