package com.demo.telecom.service;

import com.demo.telecom.dto.Number;
import com.demo.telecom.exceptions.InvalidCustomer;
import com.demo.telecom.exceptions.InvalidNumber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.demo.telecom.config.Configuration.customerMap;
import static com.demo.telecom.config.Configuration.numberMap;
import static com.demo.telecom.util.Util.addNumberAndPlan;
import static com.demo.telecom.util.Util.updateCustomerAndNumber;

@Service
public class NumberService implements TelecomService {

    @Override
    public List<Number> getAll() {
        List<Number> list = new ArrayList<>();
        numberMap.forEach((k, v) -> {
            list.add(v);
        });
        return list;
    }

    @Override
    public Number getById(long id) throws InvalidNumber {
        if(!numberMap.keySet().contains(id)){
            throw new InvalidNumber();
        }
        return numberMap.get(id);
    }

    @Override
    public List<Number> add(List list) throws InvalidCustomer, InvalidNumber {
        List<Number> addedList = new ArrayList<>();
        for (Object ob : list) {
            Number number = (Number) ob;
            if(!customerMap.keySet().contains(number.getCustomerId())){
               throw new InvalidCustomer();
            }
            if (numberMap.keySet().contains(number.getNumber())) {
                throw new InvalidNumber();
            }
            customerMap.get(number.getCustomerId()).getNumberList().add(number);
            addNumberAndPlan(number);
            addedList.add(number);

        }

        return addedList;
    }

    @Override
    public void edit(Object ob) throws InvalidCustomer, InvalidNumber {

        Number number = (Number) ob;
        if(!numberMap.keySet().contains(number.getNumber())){
            throw new InvalidNumber();
        }
        updateCustomerAndNumber(number);

    }

    @Override
    public void deleteById(long id) throws InvalidNumber {
        if (numberMap.keySet().contains(id)) {
            numberMap.remove(id);
        } else {
            throw new InvalidNumber();
        }
    }
}
