package com.demo.telecom.controller;


import com.demo.telecom.dto.Number;
import com.demo.telecom.service.TelecomFactoryImpl;
import com.demo.telecom.service.TelecomService;
import com.demo.telecom.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/number")
public class NumberController{

    private TelecomFactoryImpl factoryImpl;
    private TelecomService service;

    @Autowired
    NumberController(TelecomFactoryImpl factoryImpl){
        this.factoryImpl = factoryImpl;
        this.service =  factoryImpl.getImplementation(Type.NUMBER);
    }

    @GetMapping("/getAll")
    public List<Number> getNumbers(){
        return service.getAll();
    }



    @GetMapping("/getById")
    public Number getNumberById(@RequestParam(value = "id",required = true)long id ){
        try {
            return (Number) service.getById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity addNumbers(@RequestBody List<Number> numberList){
        List<Number> respList= null;
        try {
            respList = service.add(numberList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity(respList,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity updateNumber(@RequestBody Number number){
        try {
            service.edit(number);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity("Updated Successfully",HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteNumber(@RequestParam(value = "id",required = true)long id){
        try {
            service.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity("Deleted Successfully",HttpStatus.OK);
    }



}