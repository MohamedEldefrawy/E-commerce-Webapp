package com.vodafone.service;

import com.vodafone.exception.IncompleteUserAttributesException;
import com.vodafone.exception.NullCustomerException;
import com.vodafone.exception.NullIdException;
import com.vodafone.model.Customer;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import com.vodafone.repository.customer.CustomerRepository;
import com.vodafone.repository.customer.ICustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final ICustomerRepository customerRepository;

    private final HashService hashService;

    public CustomerService(ICustomerRepository customerRepository, HashService hashService) {
        this.customerRepository = customerRepository;
        this.hashService = hashService;
        /*Customer customer = new Customer();
        customer.setEmail("mi@gmail.com");
        customer.setUserName("mi");
        customer.setRole(Role.Customer);
        customer.setPassword(hashService.encryptPassword("12345678", customer.getEmail()));
        customer.setUserStatus(UserStatus.ACTIVATED);
        customerRepository.create(customer);

         */
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
        return customerRepository.create(customer);
    }

    public boolean update(Long id, Customer updatedCustomer) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        if (updatedCustomer == null)
            throw new NullCustomerException("Null updated customer entity is provided");
        if (get(id) == null)
            throw new NullCustomerException("Customer not found with provided id");
        return customerRepository.update(id, updatedCustomer);
    }

    public boolean updateStatusActivated(String email) {
        if (email == null)
            throw new NullPointerException("Null email is provided");
        if (customerRepository.getByMail(email) == null) {
            throw new NullCustomerException("Customer not found with email provided");
        }
        return customerRepository.updateStatusActivated(email);
    }

    public boolean delete(Long id) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        if (get(id) == null) {
            throw new NullCustomerException("Customer not found with id provided");
        }
        return customerRepository.delete(id);
    }

    public Customer get(Long id) {
        if (id == null)
            throw new NullIdException("Null customer id is provided");
        return customerRepository.get(id);
    }

    public Customer getByMail(String email) {
        if (email == null)
            throw new NullPointerException("Null Email is provided");
        return customerRepository.getByMail(email);
    }

    public Customer getByUserName(String username) {
        if (username == null)
            throw new NullPointerException("Null Username is provided");
        return customerRepository.getByUserName(username);
    }

    public List<Customer> getAll() {
        return customerRepository.getAll();
    }

    public boolean resetPassword(String email, String password) {
        if (email == null)
            throw new NullPointerException("Null Email is provided");
        if (password == null)
            throw new NullPointerException("Null Password is provided");
        if (customerRepository.getByMail(email) == null) {
            throw new NullCustomerException("Customer not found with provided email");
        }
        return customerRepository.resetPassword(email, password);
    }

    public boolean expireOtp(String userName) {
        if (userName == null)
            throw new NullPointerException("Null Username is provided");
        if (getByUserName(userName) == null) {
            throw new NullCustomerException("Customer not found with provided username");
        }
        return this.customerRepository.expireOtp(userName);
    }

}
