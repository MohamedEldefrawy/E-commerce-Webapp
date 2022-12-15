package com.vodafone.repository.customer;

import com.vodafone.model.Customer;
import com.vodafone.repository.Repository;

import java.util.Optional;

public interface ICustomerRepository extends Repository<Long, Customer> {
    boolean resetPassword(String email, String password);

    Optional<Customer> getByMail(String email);

    Optional<Customer> getByUserName(String username);

    boolean updateStatusActivated(String email);

    boolean expireOtp(String userName);
}
