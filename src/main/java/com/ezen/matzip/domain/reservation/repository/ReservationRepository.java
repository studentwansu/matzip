package com.ezen.matzip.domain.reservation.repository;

import com.ezen.matzip.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findReservationByUserCode(int userCode);
}
