package com.example.hotelbooking.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErrorDto{
    @JsonFormat
    String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:MM:SS")
    LocalDateTime responseTime;
    public ErrorDto(){
        this.responseTime= LocalDateTime.now();
    }
    public ErrorDto(String message){
        this.responseTime= LocalDateTime.now();
        this.message=message;
    }
}
