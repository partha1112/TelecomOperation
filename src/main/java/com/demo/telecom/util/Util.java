package com.demo.telecom.util;

import com.demo.telecom.dto.Number;
import com.demo.telecom.exceptions.InvalidCustomer;

import static com.demo.telecom.config.Configuration.*;
import static com.demo.telecom.config.Configuration.customerMap;

public class Util {

    public static void addNumberAndPlan(Number number) {
        if(number != null && !numberMap.keySet().contains(number.getNumber())){
            numberMap.put(number.getNumber(), number);
        }
        if( number.getPlan() != null && !planMap.keySet().contains(number.getPlan().getPlanId())){
            planMap.put(number.getPlan().getPlanId(), number.getPlan());
        }
    }

    public static boolean updateCustomerAndNumber(Number number) throws InvalidCustomer {
        boolean updated = false;
        if(customerMap.keySet().contains(number.getCustomerId())){
            if(numberMap.keySet().contains(number.getNumber())){
                customerMap.get(number.getCustomerId()).getNumberList().forEach(n->{
                    if(n.getNumber() == number.getNumber()){
                        n.setPlan(number.getPlan());
                        n.setActive(number.isActive());
                    }
                });
            }else{
                customerMap.get(number.getCustomerId()).getNumberList().add(number);
            }

            addNumberAndPlan(number);
            updated=true;
        }else {
            throw new InvalidCustomer();
        }
        return updated;
    }
}
