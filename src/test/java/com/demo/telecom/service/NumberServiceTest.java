package com.demo.telecom.service;

import com.demo.telecom.dto.Customer;
import com.demo.telecom.dto.Number;
import com.demo.telecom.dto.Plan;
import com.demo.telecom.exceptions.InvalidCustomer;
import com.demo.telecom.exceptions.InvalidNumber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NumberServiceTest {

    @Autowired
    private NumberService numberService;

    @Autowired
    private CustomerService customerService;

    @Test
    void getAll(){
        List<Number> resp =numberService.getAll();
        assertTrue(resp.size()>1);
    }

    @Test
    void getByIdSuccessCase() throws InvalidNumber {
        Number response = numberService.getById(999999991);
        assertEquals(response.getNumber(),999999991);

    }

    @Test
    void getByIdExceptionCase() {
        Exception exception = assertThrows(InvalidNumber.class, () -> {
            numberService.getById(000);
        });
        assertEquals("Invalid Number Provided", exception.getMessage());
    }

    @Test
    void addCustomersFailureCase(){

        Plan p = new Plan(333,333, 333, 333, 300);
        Number n = new Number(1111111111, 2222, p, true);

        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            numberService.add(Arrays.asList(n));
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());

    }

    @Test
    void addCustomersSuccessCase() throws InvalidNumber, InvalidCustomer {
        Plan p = new Plan(333,333, 333, 333, 300);
        Number n = new Number(77777777, 113, p, true);

        List<Number> beforeAdd = numberService.getAll();
        assertEquals( beforeAdd.size(), 5);

        List<Number> response = numberService.add(Arrays.asList(n));
        assertNotNull(response );

        List<Number> afterAdd = numberService.getAll();
        assertEquals( afterAdd.size(), 6);

        Customer customer= customerService.getById(113);
        assertEquals(customer.getNumberList().size(),2);
    }

    @Test
    void editSuccessCase() throws InvalidNumber, InvalidCustomer {
        Plan p = new Plan(111,10, 10, 10, 100);
        Number n = new Number(999999991, 112, p, false);
        numberService.edit(n);
        Number response = numberService.getById(999999991);
        assertFalse(response.isActive());
    }

    @Test
    void editExceptionCase() throws InvalidCustomer {
        Plan p = new Plan(111,10, 10, 10, 100);
        Number n = new Number(555555, 112, p, false);
        Exception exception = assertThrows(InvalidNumber.class, () -> {
            numberService.edit(n);
        });
        assertEquals("Invalid Number Provided", exception.getMessage());
    }

    @Test
    void deleteByIdScuuessCase() throws InvalidNumber, InvalidCustomer {

        Plan p = new Plan(333,333, 333, 333, 300);
        Number n = new Number(1111111111, 112, p, true);
        try {
            numberService.add(Arrays.asList(n));
        } catch (Exception e) {
            System.out.println("Number Already Available");
        }

        Customer customerBefore=customerService.getById(112L);
        assertTrue(customerBefore.getNumberList().size()==2);

        assertNotNull( numberService.getById(1111111111));
        numberService.deleteById(1111111111);
        assertEquals(numberService.getAll().size(),5);

        Customer customerAfter=customerService.getById(112L);
        assertTrue(customerBefore.getNumberList().size()==1);
    }

    @Test
    void deleteByIdExceptionCase(){
        Exception exception = assertThrows(InvalidNumber.class, () -> {
            numberService.deleteById(9889);
        });
        assertEquals("Invalid Number Provided", exception.getMessage());
    }

}
