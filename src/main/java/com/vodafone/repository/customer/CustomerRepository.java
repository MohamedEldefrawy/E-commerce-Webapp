package com.vodafone.repository.customer;

import com.vodafone.model.Customer;
import com.vodafone.model.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Objects;

@Repository
@AllArgsConstructor
public abstract class CustomerRepository implements ICustomerRepository {

    public boolean updateStatusActivated(Customer customer) {
        customer.setUserStatus(UserStatus.ACTIVATED);
        save(customer);
        return customer.getUserStatus() == UserStatus.ACTIVATED;
    }

    @Override
    public boolean expireOtp(Customer customer) {
        customer.setCode(null);
        save(customer);
        return customer.getCode() == null;
    }

    @Override
    public boolean resetPassword(Customer customer, String password) {
        //update customer's password
        customer.setPassword(password);
        //update customer's status to activated
        customer.setUserStatus(UserStatus.ACTIVATED);
        customer.setLoginAttempts(3);
        save(customer);
        return Objects.equals(customer.getPassword(), password);
    }
}
