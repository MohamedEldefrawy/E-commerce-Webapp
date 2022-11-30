package com.vodafone.service;

import com.vodafone.model.Customer;
import com.vodafone.repository.customer.CustomerRepository;

import java.util.List;

public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean create(Customer customer){
        return customerRepository.create(customer);
    }

    public boolean update(Long id, Customer updatedCustomer){
        return customerRepository.update(id,updatedCustomer);
    }
    public boolean delete(Long id){
        return customerRepository.delete(id);
    }

    public Customer get(Long id){
        return customerRepository.get(id);
    }

    public List<Customer> getAll(){
        return customerRepository.getAll();
    }
}
