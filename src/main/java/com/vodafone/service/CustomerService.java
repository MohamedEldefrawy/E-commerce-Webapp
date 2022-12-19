package com.vodafone.service;

import com.vodafone.exception.NullIdException;
import com.vodafone.exception.customer.IncompleteUserAttributesException;
import com.vodafone.exception.customer.NullCustomerException;
import com.vodafone.model.Customer;
import com.vodafone.repository.customer.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private final ICustomerRepository customerRepository;

    private final HashService hashService;

    public boolean isCustomerAttributesValid(Customer customer) {
        return customer.getUserName() != null && customer.getEmail() != null
                && customer.getPassword() != null && customer.getRole() != null
                && customer.getUserStatus() != null;
    }

    public Customer create(Customer customer) {
        if (customer == null)
            throw new NullCustomerException("Null customer entity is provided");
        if (!isCustomerAttributesValid(customer))
            throw new IncompleteUserAttributesException("Customer Data is not completed, nulls exist");
        String originalPassword = customer.getPassword();
        customer.setPassword(hashService.encryptPassword(originalPassword, customer.getEmail()));
        return customerRepository.save(customer);
    }

    //todo: may need to remove id from param
    public Customer update(Long id, Customer updatedCustomer) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        if (updatedCustomer == null)
            throw new NullCustomerException("Null updated customer entity is provided");
        return customerRepository.save(updatedCustomer);
    }

    public boolean updateStatusActivated(String email) {
        if (email == null)
            throw new NullPointerException("Null email is provided");
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if (!customer.isPresent()) {
            throw new HibernateException("Customer not found with email provided");
        }
        return customerRepository.updateStatusActivated(customer.get());
    }

    public void delete(Long id) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent())
            throw new HibernateException("Customer not found with id provided");
        customerRepository.delete(customer.get());
    }

    public Customer getById(Long id) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent())
            throw new HibernateException("Customer not found with provided id");
        return customer.get();
    }

    public Customer getByMail(String email) {
        if (email == null)
            throw new NullPointerException("Null Email is provided");
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if (!customer.isPresent())
            throw new HibernateException("No customer found");
        return customer.get();
    }

    public Customer getByUserName(String username) {
        if (username == null)
            throw new NullPointerException("Null Username is provided");
        Optional<Customer> customer = customerRepository.findCustomerByUserName(username);
        if (!customer.isPresent())
            throw new HibernateException("No customer found");
        return customer.get();
    }

    public List<Customer> getAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public boolean resetPassword(String email, String password) {
        if (email == null)
            throw new NullPointerException("Null Email is provided");
        if (password == null)
            throw new NullPointerException("Null Password is provided");
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if (!customer.isPresent()) {
            throw new HibernateException("Customer not found with provided email");
        }
        return customerRepository.resetPassword(customer.get(), password);
    }

    public boolean expireOtp(String userName) {
        if (userName == null)
            throw new NullPointerException("Null Username is provided");
        Optional<Customer> customer = customerRepository.findCustomerByUserName(userName);
        if (!customer.isPresent()) {
            throw new HibernateException("Customer not found with provided username");
        }
        return this.customerRepository.expireOtp(customer.get());
    }

}

//todo: check if thrown exceptions are needed