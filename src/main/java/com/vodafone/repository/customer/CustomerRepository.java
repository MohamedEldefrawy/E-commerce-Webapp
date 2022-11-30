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
    public boolean update(Long id, Customer updatedCustomer) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class,id);
            if(customer==null){
                return  false;
            }else {
                session.update(updatedCustomer);
                return true;
            }
        } catch (HibernateException hibernateException){
            hibernateException.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(Long id) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Customer deletedCustomer = session.get(Customer.class,id);
            if(deletedCustomer==null){
                return false;
            }else {
                Transaction transaction = session.beginTransaction();
                session.delete(deletedCustomer);
                return true;
            }
        }catch (HibernateException hibernateException){
            hibernateException.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer get(Long id) {
        Customer customer = null;
        try(Session session = hibernateConfig.getSessionFactory().openSession()) {
           customer = session.get(Customer.class,id);
        }catch (HibernateException hibernateException){
            hibernateException.printStackTrace();
        }
        return customer;
    }
    @Override
    public List<Customer> getAll() {
        return null;
    }
}
