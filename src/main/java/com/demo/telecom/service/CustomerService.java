package com.demo.telecom.service;

import com.demo.telecom.dto.Customer;
import com.demo.telecom.dto.Number;
import com.demo.telecom.exceptions.InvalidCustomer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.demo.telecom.config.Configuration.customerMap;
import static com.demo.telecom.config.Configuration.numberMap;
import static com.demo.telecom.util.Util.addNumberAndPlan;
import static com.demo.telecom.util.Util.updateCustomerAndNumber;

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
    public List<Customer> add(List list) throws InvalidCustomer {
        List<Customer> addedList = new ArrayList<>();
        for(Object ob : list){
            Customer customer = (Customer) ob;
            if(customerMap.keySet().contains(customer.getCustomerId())){
                throw new InvalidCustomer();
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
    public Customer getById(long id) throws InvalidCustomer {
        if(!customerMap.keySet().contains(id)){
            throw new InvalidCustomer();
        }
        return customerMap.get(id);
    }



    @Override
    public void edit(Object ob) throws InvalidCustomer {
        Customer customer = (Customer) ob;
        if(customerMap.keySet().contains(((Customer) ob).getCustomerId())){
            customerMap.put(customer.getCustomerId(), customer);
            customer.getNumberList().forEach(n->{addNumberAndPlan(n);});
        }else {
            throw new InvalidCustomer();
        }


    }

    @Override
    public void deleteById(long id) throws InvalidCustomer {
       if(customerMap.keySet().contains(id)){
           customerMap.remove(id);
       }else{
           throw new InvalidCustomer();
       }
    }

    public boolean updateNumber(Number number) throws InvalidCustomer {
        return updateCustomerAndNumber(number);
    }

    public List<Number> getAllNumbers(Long customerId) throws InvalidCustomer {
        if(!customerMap.keySet().contains(customerId)){
            throw new InvalidCustomer();
        }
        return customerMap.get(customerId).getNumberList();
    }

    public void updateNumberStatus(String status,Long customerId,Long number) throws InvalidCustomer {
        if(customerMap.keySet().contains(customerId)){
            customerMap.get(customerId).getNumberList().forEach( n -> {
                if(n.getNumber()==number){
                    boolean  isActive= status.equalsIgnoreCase("active")?true:false;
                    n.setActive( isActive );
                    numberMap.get(number).setActive(isActive);
                }
            });
        }else {
            throw new InvalidCustomer();
        }

    }

    public void changeCustomerNumbers(Number number, Long oldCustomer) throws InvalidCustomer {
        Long newCustomerId = number.getCustomerId();

        if(!customerMap.keySet().contains(number.getCustomerId()) || !customerMap.keySet().contains(oldCustomer) ){
            throw new InvalidCustomer();
        }

        customerMap.get(oldCustomer).getNumberList().removeIf( n -> n.getNumber() == number.getNumber());
        customerMap.get(newCustomerId).getNumberList().add(number);

    }
}
