package com.demo.telecom.exceptions;

import com.demo.telecom.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidNumber.class)
    public ResponseEntity<ErrorDto> numberNotFoundHaldler(InvalidNumber e){
       return new ResponseEntity(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "Please check the Customer details you have entered"),
               HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidCustomer.class)
    public ResponseEntity<ErrorDto> customerNotFoundHaldler(InvalidCustomer e){
        return new ResponseEntity(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "Please check the Number details you have entered"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
