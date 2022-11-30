package com.vodafone.controller;

import com.vodafone.model.Customer;
import com.vodafone.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public boolean create(Customer customer){
        return customerService.create(customer);
    }

    @PostMapping
    public boolean update(Long id, Customer updatedCustomer){
        return customerService.update(id,updatedCustomer);
    }

    @DeleteMapping
    public boolean delete(Long id){
        return customerService.delete(id);
    }

    @GetMapping("{id}")
    public Customer get(@PathVariable("id") Long id){
        return customerService.get(id);
    }

    @GetMapping
    public List<Customer> getAll(){
        return customerService.getAll();
    }
}
