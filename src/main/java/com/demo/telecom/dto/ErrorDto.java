package com.demo.telecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private int code;
    private String message;
    private String details;
}
