package com.vodafone.repository.customer;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Customer;
import com.vodafone.model.UserStatus;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public abstract class CustomerRepository implements ICustomerRepository {
    private final HibernateConfig hibernateConfig;

    public boolean updateStatusActivated(Customer customer) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            customer.setUserStatus(UserStatus.ACTIVATED);
            session.update(customer);
            transaction.commit();
            return true;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean expireOtp(Customer customer) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            customer.setCode(null);
            session.update(customer);
            transaction.commit();
            return true;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean resetPassword(Customer customer, String password) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            //update customer's password
            customer.setPassword(password);
            //update customer's status to activated
            customer.setUserStatus(UserStatus.ACTIVATED);
            customer.setLoginAttempts(3);
            session.update(customer);
            session.beginTransaction().commit();
            return true;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
    }
}
