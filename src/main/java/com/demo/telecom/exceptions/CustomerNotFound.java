package com.demo.telecom.exceptions;

public class CustomerNotFound extends Exception{
    public CustomerNotFound(){
        super("Invalid Customer Provided");
    }
}
