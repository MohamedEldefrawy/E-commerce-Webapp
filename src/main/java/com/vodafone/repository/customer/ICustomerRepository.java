package com.vodafone.repository.customer;

import com.vodafone.model.Customer;
import com.vodafone.model.Email;
import com.vodafone.model.Order;
import com.vodafone.repository.Repository;

import java.util.List;

public interface ICustomerRepository extends Repository<Customer> {
    boolean resetPassword(String email, String password);
    Email requestResetPassword(String email);
    Email sendActivationEmail(String email,String OTP);
    public Customer getByMail(String email);
    public boolean updateStatusActivated(String email);
}
