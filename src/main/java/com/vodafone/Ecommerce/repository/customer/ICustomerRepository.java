//package com.vodafone.Ecommerce.repository.customer;
//
//import com.vodafone.Ecommerce.model.Customer;
//import com.vodafone.Ecommerce.repository.Repository;
//
//import java.util.Optional;
//
//public interface ICustomerRepository extends Repository<Long, Customer> {
//    boolean resetPassword(Customer customer, String password);
//
//    Optional<Customer> getByMail(String email);
//
//    Optional<Customer> getByUserName(String username);
//
//    boolean updateStatusActivated(Customer customer);
//
//    boolean expireOtp(Customer customer);
//}
