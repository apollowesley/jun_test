package com.coldfeng.service;

import javax.jws.WebService;

import org.springframework.beans.BeanUtils;

import com.coldfeng.dao.HelloWord;
import com.coldfeng.model.Customer;

@WebService
public class HelloWorldService implements HelloWord {
    public Customer hello(Customer customer) {
        System.out.println("hello " + customer.getName());
       Customer customer2=new Customer();
       BeanUtils.copyProperties(customer, customer2);
        return customer;
    }
}
