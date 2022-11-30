package com.vodafone.repository.customer;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository implements ICustomerRepository{

    private final HibernateConfig hibernateConfig;

    public CustomerRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    /*
        +register():
        +verifyEmail():
        + addProductToCart( product : Product):
        + updateCart():
        + removeProductFromCart():
        + emptyCart():void
        +reviewFinalOrder():

        +submitFinalOrder():
     */


    @Override
    public boolean create(Customer customer) {
        try(Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(customer);
            transaction.commit();
            return true;
        }catch (HibernateException hibernateException){
            hibernateException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Customer updatedEntity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Customer get(Long id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }
}
