package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.PresidentialSuitOfADay;
import com.example.hotelbooking.entity.Reservation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface ReservationRepository  extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT r FROM Reservation r WHERE r.reservationId= :reservationId")
    Reservation findByReservationId(@Param("reservationId") UUID reservationId);

    @Query(value = "SELECT r FROM Reservation r WHERE r.reservationId= :reservationId and r.status='RESERVED'")
    Reservation findByActiveReservationId(@Param("reservationId") UUID reservationId);
}
