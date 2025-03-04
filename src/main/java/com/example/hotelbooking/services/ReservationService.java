package com.example.hotelbooking.services;

import com.example.hotelbooking.AppConfig;
import com.example.hotelbooking.Dto.BookingInfo;
import com.example.hotelbooking.Dto.BookingRequest;
import com.example.hotelbooking.Dto.BookingResponse;
import com.example.hotelbooking.entity.Reservation;
import com.example.hotelbooking.entity.PresidentialSuitOfADay;
import com.example.hotelbooking.repository.ReservationRepository;
import com.example.hotelbooking.repository.PresidentialSuitOfADayRepository;
import com.example.hotelbooking.utility.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PresidentialSuitOfADayRepository presidentialSuitOfADayRepository;
    private final AppConfig appConfig;
    @Autowired
    ReservationService(ReservationRepository reservationRepository,
                       PresidentialSuitOfADayRepository presidentialSuitOfADayRepository,
                       AppConfig appConfig) {
        this.reservationRepository = reservationRepository;
        this.presidentialSuitOfADayRepository = presidentialSuitOfADayRepository;
        this.appConfig = appConfig;
    }

    @Transactional
    public BookingResponse makeReservation(BookingRequest bookingRequest){
        List<PresidentialSuitOfADay> presidentialSuitOfADayList = presidentialSuitOfADayRepository.findByDateForUpdate(bookingRequest.getStartDate(), bookingRequest.getEndDate());
        LocalDate startDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(bookingRequest.getStartDate()) );
        LocalDate endDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(bookingRequest.getEndDate()) );
        Period age = Period.between(startDate, endDate);

        if(presidentialSuitOfADayList.size() == age.getDays()+1){
            Reservation reservation = appConfig.modelMapper().map(bookingRequest, Reservation.class);
            reservation.setStatus(ReservationStatus.RESERVED);
            reservation.setReservationId(UUID.randomUUID());
            reservationRepository.save(reservation);
            presidentialSuitOfADayList.forEach(presidentialSuitOfADay -> {
                presidentialSuitOfADay.setReserved(true);
                presidentialSuitOfADay.setReservation(reservation);
            });
            presidentialSuitOfADayRepository.saveAll(presidentialSuitOfADayList);
            return appConfig.modelMapper().map(reservation, BookingResponse.class);
        }
        return null;
    }

    public BookingInfo getReservationInfo(UUID reservationId){
        Reservation reservation =  reservationRepository.findByReservationId(reservationId);
        if(reservation!=null){
            BookingInfo bookingInfo = appConfig.modelMapper().map(reservation, BookingInfo.class);
            return bookingInfo;
        }
        return null;
    }
    @Transactional
    public String cancelReservation(UUID reservationId){
        Reservation reservation =  reservationRepository.findByActiveReservationId(reservationId);
        if(reservation!=null){
            List<PresidentialSuitOfADay> presidentialSuitOfADayList = presidentialSuitOfADayRepository.findByReservation(reservation);
            presidentialSuitOfADayList.forEach(presidentialSuitOfADay -> {
                presidentialSuitOfADay.setReserved(false);
                presidentialSuitOfADay.setReservation(null);
            });
            presidentialSuitOfADayRepository.saveAll(presidentialSuitOfADayList);
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(reservation);
            return new String("reservation cancelled successfully");
        }
        return null;
    }
}
