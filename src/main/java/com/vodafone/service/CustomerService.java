package com.vodafone.service;

import com.vodafone.exception.NullIdException;
import com.vodafone.exception.customer.IncompleteUserAttributesException;
import com.vodafone.exception.customer.NullCustomerException;
import com.vodafone.model.Customer;
import com.vodafone.repository.customer.ICustomerRepository;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final ICustomerRepository customerRepository;

    private final HashService hashService;

    public CustomerService(ICustomerRepository customerRepository, HashService hashService) {
        this.customerRepository = customerRepository;
        this.hashService = hashService;
    }

    public boolean isCustomerAttributesValid(Customer customer) {
        return customer.getUserName() != null && customer.getEmail() != null
                && customer.getPassword() != null && customer.getRole() != null
                && customer.getUserStatus() != null;
    }

    public boolean create(Customer customer) {
        if (customer == null)
            throw new NullCustomerException("Null customer entity is provided");
        if (!isCustomerAttributesValid(customer))
            throw new IncompleteUserAttributesException("Customer Data is not completed, nulls exist");
        String originalPassword = customer.getPassword();
        customer.setPassword(hashService.encryptPassword(originalPassword, customer.getEmail()));
        return customerRepository.create(customer).isPresent();
    }

    public boolean update(Long id, Customer updatedCustomer) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        if (updatedCustomer == null)
            throw new NullCustomerException("Null updated customer entity is provided");
        Optional<Customer> customer = customerRepository.getById(id);
        if (!customer.isPresent())
            throw new HibernateException("Customer not found with provided id");
        return customerRepository.update(id, updatedCustomer);
    }

    public boolean updateStatusActivated(String email) {
        if (email == null)
            throw new NullPointerException("Null email is provided");
        Optional<Customer> customer = customerRepository.getByMail(email);
        if (!customer.isPresent()) {
            throw new HibernateException("Customer not found with email provided");
        }
        return customerRepository.updateStatusActivated(customer.get());
    }

    public boolean delete(Long id) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        Optional<Customer> customer = customerRepository.getById(id);
        if (!customer.isPresent())
            throw new HibernateException("Customer not found with id provided");
        return customerRepository.delete(id);
    }

    public Customer getById(Long id) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        Optional<Customer> customer = customerRepository.getById(id);
        if (!customer.isPresent())
            throw new HibernateException("Customer not found with provided id");
        return customer.get();
    }

    public Customer getByMail(String email) {
        if (email == null)
            throw new NullPointerException("Null Email is provided");
        Optional<Customer> customer = customerRepository.getByMail(email);
        if (!customer.isPresent())
            throw new HibernateException("No customer found");
        return customer.get();
    }

    public Customer getByUserName(String username) {
        if (username == null)
            throw new NullPointerException("Null Username is provided");
        Optional<Customer> customer = customerRepository.getByUserName(username);
        if (!customer.isPresent())
            throw new HibernateException("No customer found");
        return customer.get();
    }

    public List<Customer> getAll() {
        Optional<List<Customer>> customer = customerRepository.getAll();
        if (!customer.isPresent())
            throw new HibernateException("Customer not found with provided id");
        return customer.get();
    }

    public boolean resetPassword(String email, String password) {
        if (email == null)
            throw new NullPointerException("Null Email is provided");
        if (password == null)
            throw new NullPointerException("Null Password is provided");
        Optional<Customer> customer = customerRepository.getByMail(email);
        if (!customer.isPresent()) {
            throw new HibernateException("Customer not found with provided email");
        }
        return customerRepository.resetPassword(customer.get(), password);
    }

    public boolean expireOtp(String userName) {
        if (userName == null)
            throw new NullPointerException("Null Username is provided");
        Optional<Customer> customer = customerRepository.getByUserName(userName);
        if (!customer.isPresent()) {
            throw new HibernateException("Customer not found with provided username");
        }
        return this.customerRepository.expireOtp(customer.get());
    }

}
