package com.vodafone.repository.customer;

import com.vodafone.model.Customer;
import com.vodafone.repository.Repository;

public interface ICustomerRepository extends Repository<Long, Customer> {
    boolean resetPassword(String email, String password);

    Customer getByMail(String email);

    Customer getByUserName(String username);

    boolean updateStatusActivated(String email);

    boolean expireOtp(String userName);
}
