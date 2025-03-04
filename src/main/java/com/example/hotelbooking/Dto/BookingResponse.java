package com.example.hotelbooking.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class BookingResponse {
    UUID reservationId;
    String message;
}
