package com.demo.telecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private long customerId;
    private String name;
    private String address;
    private CopyOnWriteArrayList<Number> numberList;




}
