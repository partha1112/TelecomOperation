package com.demo.telecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Plan {

    private long planId;
    private int amount;
    private int calls;
    private int sms;
    private int talkTime;
}
