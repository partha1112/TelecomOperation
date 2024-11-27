package com.demo.telecom.service;

import com.demo.telecom.dto.Customer;
import com.demo.telecom.dto.Number;
import com.demo.telecom.dto.Plan;
import com.demo.telecom.exceptions.CustomerNotFound;
import com.demo.telecom.exceptions.NumberNotFound;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.demo.telecom.config.Configuration.*;

@Service
public class CustomerService implements TelecomService{

    @Override
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        customerMap.forEach( (k,v)->{
            list.add(v);
        });
        return list;
    }

    @Override
    public List<Customer> add(List list) throws CustomerNotFound {
        List<Customer> addedList = new ArrayList<>();
        for(Object ob : list){
            Customer customer = (Customer) ob;
            if(customerMap.keySet().contains(customer.getCustomerId())){
                throw new CustomerNotFound();
            }else{
                customerMap.put(customer.getCustomerId(), customer);
                customer.getNumberList().forEach(number -> {
                    addNumberAndPlan(number);
                });

                addedList.add(customer);
            }
        }

        return addedList;
    }

    @Override
    public Customer getById(long id) throws CustomerNotFound {
        if(!customerMap.keySet().contains(id)){
            throw new CustomerNotFound();
        }
        return customerMap.get(id);
    }



    @Override
    public void edit(Object ob) throws CustomerNotFound {
        Customer customer = (Customer) ob;
        if(customerMap.keySet().contains(((Customer) ob).getCustomerId())){
            customerMap.put(customer.getCustomerId(), customer);
        }else {
            throw new CustomerNotFound();
        }


    }

    @Override
    public void deleteById(long id) throws CustomerNotFound {
       if(customerMap.keySet().contains(id)){
           customerMap.remove(id);
       }else{
           throw new CustomerNotFound();
       }
    }

    public boolean updateNumber(Number number) throws CustomerNotFound {

        boolean updated = false;
        if(customerMap.keySet().contains(number.getCustomerId())){
            if(numberMap.keySet().contains(number.getNumber())){
                customerMap.get(number.getCustomerId()).getNumberList().forEach(n->{
                    if(n.getNumber() == number.getNumber()){
                        n.setCustomerId(number.getCustomerId());
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
            throw new CustomerNotFound();
        }
        return updated;
    }

    public List<Number> getAllNumbers(Long customerId) throws CustomerNotFound {
        if(!customerMap.keySet().contains(customerId)){
            throw new CustomerNotFound();
        }
        return customerMap.get(customerId).getNumberList();
    }

    public void updateNumberStatus(String status,Long customerId,Long number) throws CustomerNotFound {
        if(customerMap.keySet().contains(customerId)){
            customerMap.get(customerId).getNumberList().forEach( n -> {
                if(n.getNumber()==number){
                    boolean  isActive= status.equalsIgnoreCase("active")?true:false;
                    n.setActive( isActive );
                    numberMap.get(number).setActive(isActive);
                }
            });
        }else {
            throw new CustomerNotFound();
        }

    }
    private static void addNumberAndPlan(Number number) {
        if(number != null && !numberMap.keySet().contains(number.getNumber())){
            numberMap.put(number.getNumber(), number);
        }
        if( number.getPlan() != null && !planMap.keySet().contains(number.getPlan().getPlanId())){
            planMap.put(number.getPlan().getPlanId(), number.getPlan());
        }
    }
}
