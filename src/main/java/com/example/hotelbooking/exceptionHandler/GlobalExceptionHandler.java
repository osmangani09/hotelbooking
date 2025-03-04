package com.example.hotelbooking.exceptionHandler;


import com.example.hotelbooking.Dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorDto> handleNodataFoundException(
            NoDataFoundException ex) {
        ErrorDto errorDto = new ErrorDto(new String(ex.getMessage()));
        return new ResponseEntity(errorDto, ex.getHttpStatus());
    }

    @ExceptionHandler(ReservationNotAvailableException.class)
    public ResponseEntity<ErrorDto> handleReservationNotAvailableException(
            ReservationNotAvailableException ex) {
        ErrorDto errorDto = new ErrorDto(new String(ex.getMessage()));
        return new ResponseEntity(errorDto, ex.getHttpStatus());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String,String> errorMap =  new HashMap<>();
        for (FieldError error : errors) {
            errorMap.put(error.getField(),error.getDefaultMessage());
        }
        return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
    }

}
