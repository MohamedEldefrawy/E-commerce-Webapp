package com.vodafone.repository.customer;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Cart;
import com.vodafone.model.Customer;
import com.vodafone.model.Role;
import com.vodafone.model.UserStatus;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository implements ICustomerRepository {

    private final HibernateConfig hibernateConfig;
    private final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

    public CustomerRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Override
    public Optional<Long> create(Customer customer) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            //set customer's default status before verification
            customer.setCart(new Cart(customer, new ArrayList<>()));

            session.save(customer.getCart());

            customer.setUserStatus(UserStatus.DEACTIVATED);
            customer.setRole(Role.Customer);

            Long customerId = (Long) session.save(customer);
            transaction.commit();
            return Optional.ofNullable(customerId);
        } catch (HibernateException hibernateException) {
            logger.warn(hibernateException.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Long id, Customer updatedCustomer) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            customer.setCode(updatedCustomer.getCode());
            customer.setLoginAttempts(updatedCustomer.getLoginAttempts());
            customer.setUserStatus(updatedCustomer.getUserStatus());
            session.update(customer);
            transaction.commit();
            return true;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }

    }

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
    public boolean delete(Long id) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Customer deletedCustomer = session.get(Customer.class, id);
            Transaction transaction = session.beginTransaction();
            session.delete(deletedCustomer);
            transaction.commit();
            return true;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Customer> getById(Long id) {
        Customer customer = null;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            customer = session.get(Customer.class, id);
        } catch (HibernateException | NoResultException hibernateException) {
            hibernateException.printStackTrace();
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> getByMail(String email) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            try {
                List<Customer> customers = session.createQuery("SELECT customer from Customer customer where customer.email=: email", Customer.class).setParameter("email", email).list();
                return Optional.of(customers.get(0));
            } catch (NoResultException e) {
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> getByUserName(String username) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            try {
                List<Customer> customers = session.createQuery("SELECT customer from Customer customer where customer.userName=: username", Customer.class).setParameter("username", username).list();
                return Optional.of(customers.get(0));
            } catch (NoResultException e) {
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Customer>> getAll() {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("From Customer", Customer.class).list());
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return Optional.of(new ArrayList<>());
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
