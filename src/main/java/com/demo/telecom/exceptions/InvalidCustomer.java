package com.demo.telecom.exceptions;

public class InvalidCustomer extends Exception{
    public InvalidCustomer(){
        super("Invalid Customer Provided");
    }
}
