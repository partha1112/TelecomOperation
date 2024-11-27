package com.demo.telecom.config;

import com.demo.telecom.dto.Customer;
import com.demo.telecom.dto.Number;
import com.demo.telecom.dto.Plan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@org.springframework.context.annotation.Configuration
@Profile("test")
public class Configuration {

    public static Map<Long,Plan> planMap = new HashMap<>();
    public static Map<Long,Number> numberMap = new HashMap<>();
    public static Map<Long,Customer> customerMap = new HashMap<>();

    //Loading initial data for Cutomers, Number & Plan
    @Bean
    public Object loadInitialData(){


    for(int i=1; i<=5; i++){

        Plan p = new Plan(110+i,i*10, i*10, i*10, i*100);
        planMap.put(p.getPlanId(),p);

        Number n = new Number(999999990+i, 111+i, p, true);
        numberMap.put(n.getNumber(), n);

        Customer c = new Customer(111+i,"Customer"+i, "address : "+i, new CopyOnWriteArrayList<>( Arrays.asList(n)));
        customerMap.put(c.getCustomerId(),c);
    }

        System.out.println(customerMap);

    return null;


    }

}
