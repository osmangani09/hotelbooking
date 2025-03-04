package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.PresidentialSuitOfADay;
import com.example.hotelbooking.entity.Reservation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PresidentialSuitOfADayRepository extends JpaRepository<PresidentialSuitOfADay, Long> {


    @Query(value = "SELECT key FROM PresidentialSuitOfADay key WHERE key.availableDate>= :startDate AND key.availableDate<= :endDate AND key.reserved=false")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<PresidentialSuitOfADay> findByDateForUpdate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT key FROM PresidentialSuitOfADay key WHERE key.availableDate= :currentDate")
    PresidentialSuitOfADay findByAvailableDate(@Param("currentDate") Date currentDate);

    @Query(value = "SELECT key FROM PresidentialSuitOfADay key WHERE key.reservation= :reservation")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<PresidentialSuitOfADay> findByReservation(@Param("reservation") Reservation reservation);
}
