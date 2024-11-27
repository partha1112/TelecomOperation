package com.demo.telecom.service;

import com.demo.telecom.dto.Customer;
import com.demo.telecom.exceptions.CustomerNotFound;

import java.util.List;

public interface TelecomService {

    List getAll();

    Object getById(long id) throws CustomerNotFound;

    List add(List list) throws CustomerNotFound;
    void edit(Object obj) throws CustomerNotFound;
    void deleteById(long id) throws CustomerNotFound;
}
