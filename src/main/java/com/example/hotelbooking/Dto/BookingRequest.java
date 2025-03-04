package com.example.hotelbooking.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BookingRequest {

    @NotNull(message = "mail id may not be null")
    @Email(message = "mail id is not valid")
    String email;
    @NotNull(message = "first name may not be null")
    @NotBlank(message = "first name may not be blank")
    String firstName;
    @NotNull(message = "last name may not be null")
    @NotBlank(message = "last name may not be blank")
    String lastName;
    @Max(value = 3,message = "guest count can`t be more than 3")
    @Min(value = 1,message = "guest count can`t be less than 1")
    Integer guestCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date endDate;

}
