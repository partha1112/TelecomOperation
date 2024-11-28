package com.demo.telecom.controller;

import com.demo.telecom.dto.Customer;
import com.demo.telecom.dto.Number;
import com.demo.telecom.exceptions.InvalidCustomer;
import com.demo.telecom.service.CustomerService;
import com.demo.telecom.service.TelecomFactoryImpl;
import com.demo.telecom.service.TelecomService;
import com.demo.telecom.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    private TelecomFactoryImpl factoryImpl;
    private TelecomService service;

    @Autowired
    CustomerController(TelecomFactoryImpl factoryImpl){
        this.factoryImpl = factoryImpl;
        this.service =  factoryImpl.getImplementation(Type.CUSTOMER);
    }

    @GetMapping("/getAll")
    public List<Customer> getCustomers(){
        return service.getAll();
    }



    @GetMapping("/getById")
    public Customer getCustomerById(@RequestParam(value = "id",required = true)long id ){
        try {
            return (Customer) service.getById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody List<Customer> customerList){
        List<Customer> respList= null;
        try {
            respList = service.add(customerList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity(respList,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity updateCustomer(@RequestBody Customer customer){
        try {
            service.edit(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity("Updated Successfully",HttpStatus.OK);
    }

    @PatchMapping("/editNumber")
    public ResponseEntity changeNumber(@RequestBody Number number){
        try {
            ((CustomerService) service).updateNumber(number);
        } catch (InvalidCustomer e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity("Updated Successfully",HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam(value = "id",required = true)long id){
        try {
            service.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity("Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/getNumbers")
    public List<Number> getNumbersByCusomer(@RequestParam(name = "customer_id",required = true)Long customerId){
        try {
            return ((CustomerService) service).getAllNumbers(customerId);
        } catch (InvalidCustomer e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updateNumberStatus")
    public ResponseEntity updateNumberStatus(@RequestParam(value = "status",required = true)String status,
                                             @RequestParam(value = "customer_id",required = true)Long customerId,
                                             @RequestParam(value = "number",required = true)Long number){
        try {
            ((CustomerService) service).updateNumberStatus(status,customerId,number);
        } catch (InvalidCustomer e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity("Updated Successfully",HttpStatus.OK);
    }

    @PostMapping("/changeNumber")
    public ResponseEntity changeNumber(@RequestBody Number number,
            @RequestParam(value = "old_customer_id",required = true)Long oldCustomerId){
        try {
            ((CustomerService) service).changeCustomerNumbers(number,oldCustomerId);
        } catch (InvalidCustomer e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity("Updated Successfully",HttpStatus.OK);
    }



}
