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
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    void getAll(){
       List<Customer> response = customerService.getAll();
        assertTrue( response.size()==5);
    }

    @Test
    void getByIdSuccessCase() throws InvalidCustomer {
        Customer response = customerService.getById(112);
        assertNotNull(response.getName());

    }

    @Test
    void getByIdExceptionCase() {
        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            customerService.getById(000);
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());
    }

    @Test
    void addCustomersFailureCase(){

        Plan p = new Plan(333,333, 333, 333, 300);
        Number n = new Number(1111111111, 2222, p, true);
        Customer c = new Customer(113,"Customer99", "address : 99", new CopyOnWriteArrayList<>(Arrays.asList(n)));

        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            customerService.add(Arrays.asList(c));
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());

    }

    @Test
    void addCustomersSuccessCase() throws InvalidCustomer {
        Plan p = new Plan(333,333, 333, 333, 300);
        Number n = new Number(1111111111, 99, p, true);
        Customer c = new Customer(99,"Customer99", "address : 99", new CopyOnWriteArrayList<>(Arrays.asList(n)));
        List<Customer> response = customerService.add(Arrays.asList(c));
        assertNotNull(response );
        List<Customer> all = customerService.getAll();
        assertEquals( all.size(), 6);
    }

    @Test
    void editSuccessCase() throws InvalidCustomer {
        Plan p = new Plan(111,10, 10, 10, 100);
        Number n = new Number(999999991, 112, p, true);
        Customer c = new Customer(112,"CustomerEditted", "address : 1", new CopyOnWriteArrayList<>(Arrays.asList(n)));
        customerService.edit(c);
        Customer response = customerService.getById(112);
        assertEquals(response.getName(),"CustomerEditted");
    }

    @Test
    void editExceptionCase() throws InvalidCustomer {

        Customer c = new Customer(99988,"CustomerEditted", "address : 1", new CopyOnWriteArrayList<>());
        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            customerService.edit(c);
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());
    }

    @Test
    void deleteByIdScuuessCase() throws InvalidCustomer {

        Plan p = new Plan(333,333, 333, 333, 300);
        Number n = new Number(1111111111, 2222, p, true);
        Customer c = new Customer(99,"Customer99", "address : 99", new CopyOnWriteArrayList<>( Arrays.asList(n)));
        try {
           customerService.add(Arrays.asList(c));
        } catch (InvalidCustomer e) {
            System.out.println("Customer Already Available");
        }
        assertNotNull( customerService.getById(99));
        customerService.deleteById(99);
        assertEquals(customerService.getAll().size(),5);

    }

    @Test
    void deleteByIdExceptionCase(){
        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            customerService.deleteById(9889);
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());
    }

    @Test
    void updateNumberSuccessCase() throws InvalidNumber, InvalidCustomer {
        Number n = new Number(88888888, 114, null, true);
        boolean resp = customerService.updateNumber(n);
        assertTrue(resp);
        Customer customer = customerService.getById(114);
        assertEquals(customer.getNumberList().size(),2);
    }

    @Test
    void updateExceptionCase(){

        Number n = new Number(1111111111, 2222, null, true);
        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            customerService.updateNumber(n);
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());
    }

    @Test
    void getAllNumbersSuccessCase() throws InvalidCustomer {
        List<Number> allNumbers = customerService.getAllNumbers(116L);
        assertTrue(allNumbers.size()>0);
    }

    @Test
    void getAllNumbersExceptionCase() throws InvalidCustomer {
        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            customerService.getAllNumbers(2000L);
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());
    }

    @Test
    void changeCustomerNumbersSuccessCase() throws InvalidCustomer {
        Plan p = new Plan(111,10, 10, 10, 100);
        Number n = new Number(999999991, 115, p, true);
        customerService.changeCustomerNumbers(n,112L);
        Customer resp =customerService.getById(115);
        assertTrue(resp.getNumberList().size()==2);
    }

    @Test
    void changeCustomerNumbersExceptionCase() throws InvalidCustomer {
        Plan p = new Plan(111,10, 10, 10, 100);
        Number n = new Number(999999991, 999, p, true);
        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            customerService.changeCustomerNumbers(n,112L);
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());
    }

    @Test
    void changeCustomerNumbersExceptionCase2() throws InvalidCustomer {
        Plan p = new Plan(111,10, 10, 10, 100);
        Number n = new Number(999999991, 115, p, true);
        Exception exception = assertThrows(InvalidCustomer.class, () -> {
            customerService.changeCustomerNumbers(n,999L);
        });
        assertEquals("Invalid Customer Provided", exception.getMessage());
    }

}
