package com.demo.telecom.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Number {
    private long number;
    private long customerId;
    private Plan plan;
    private boolean isActive;
}
