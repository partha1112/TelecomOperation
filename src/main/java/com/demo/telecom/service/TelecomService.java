package com.demo.telecom.service;

import java.util.List;

public interface TelecomService {

    List getAll();

    Object getById(long id) throws Exception;

    List add(List list) throws Exception;
    void edit(Object obj) throws Exception;
    void deleteById(long id) throws Exception;
}
