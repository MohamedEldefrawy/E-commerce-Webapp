package com.vodafone.repository.customer;

import com.vodafone.model.Customer;
import com.vodafone.repository.Repository;

import java.util.Optional;

public interface ICustomerRepository extends Repository<Long, Customer> {
    boolean resetPassword(Customer customer, String password);

    Optional<Customer> getByMail(String email);

    Optional<Customer> getByUserName(String username);

    boolean updateStatusActivated(Customer customer);

    boolean expireOtp(Customer customer);
}
