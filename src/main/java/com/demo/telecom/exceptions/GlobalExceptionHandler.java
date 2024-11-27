package com.demo.telecom.exceptions;

import com.demo.telecom.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberNotFound.class)
    public ResponseEntity<ErrorDto> numberNotFoundHaldler(NumberNotFound e){
       return new ResponseEntity(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "Please check the Customer details you have entered"),
               HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerNotFound.class)
    public ResponseEntity<ErrorDto> customerNotFoundHaldler(CustomerNotFound e){
        return new ResponseEntity(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), "Please check the Number details you have entered"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
