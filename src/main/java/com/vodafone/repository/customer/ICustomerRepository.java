package com.vodafone.repository.customer;

import com.vodafone.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ICustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findCustomerByEmail(String email);

    Optional<Customer> findCustomerByUserName(String userName);
}
