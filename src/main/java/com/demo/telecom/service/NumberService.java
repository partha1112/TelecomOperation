package com.demo.telecom.service;

import com.demo.telecom.dto.Number;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.demo.telecom.config.Configuration.numberMap;

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
    public Number getById(long id) {
        return numberMap.get(id);
    }

    @Override
    public List<Number> add(List list) {
        List<Number> addedList = new ArrayList<>();
        for (Object ob : list) {
            Number number = (Number) ob;
            if (numberMap.keySet().contains(number.getCustomerId())) {
                System.out.println("Log for ID available already");
            } else {
                numberMap.put(number.getCustomerId(), number);
                addedList.add(number);
            }
        }

        return addedList;
    }

    @Override
    public void edit(Object ob) {
        Number number = (Number) ob;
        numberMap.put(number.getNumber(), number);

    }

    @Override
    public void deleteById(long id) {
        if (numberMap.keySet().contains(id)) {
            numberMap.remove(id);
        } else {
            System.out.println("Log for id not aviable");
        }
    }
}
