package com.vodafone.service;

import com.vodafone.model.Customer;
import com.vodafone.model.Email;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import com.vodafone.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        Customer customer = new Customer(UserStatus.ACTIVATED);
        customer.setEmail("mi@gmail.com");
        customer.setUserName("mi");
        customer.setRole(Role.Customer);
        customer.setPassword("1234");
        customerRepository.create(customer);
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
    public Customer getByMail(String email){
        return customerRepository.getByMail(email);
    }

    public List<Customer> getAll(){
        return customerRepository.getAll();
    }

    public boolean resetPassword(String email, String password){ return customerRepository.resetPassword(email,password); }

    public Email requestResetPassword(String email){ return customerRepository.requestResetPassword(email); }

    public Email sendActivationEmail(String email, String OTP){ return customerRepository.sendActivationEmail(email, OTP); }
}
