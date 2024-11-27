package com.demo.telecom.service;

import com.demo.telecom.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelecomFactoryImpl {


    private CustomerService customerService;
    private NumberService numberService;

    @Autowired
    public TelecomFactoryImpl(CustomerService customerService, NumberService numberService) {
        this.customerService = customerService;
        this.numberService=numberService;

    }

    public TelecomService getImplementation(Type type){

        if(type == Type.CUSTOMER){
            return customerService;
        }else {
         return numberService;
        }


    }
}
