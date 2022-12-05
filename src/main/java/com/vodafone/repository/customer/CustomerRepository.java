package com.vodafone.repository.customer;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository implements ICustomerRepository {

    private final HibernateConfig hibernateConfig;

    public CustomerRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Override
    public boolean create(Customer customer) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            //set customer's default status before verification
            customer.setCart(new Cart(customer, new ArrayList<>()));
            session.persist(customer.getCart());
            customer.setUserStatus(UserStatus.DEACTIVATED);
            customer.setRole(Role.Customer);

            session.persist(customer);
            transaction.commit();
            return true;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Customer updatedCustomer) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer == null) {
                return false;
            } else {
                session.update(updatedCustomer);
                session.beginTransaction().commit();
                return true;
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }

    }

    public boolean updateStatusActivated(String email) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Customer customer = getByMail(email);
            if (customer == null) {
                return false;
            } else {
                customer.setUserStatus(UserStatus.ACTIVATED);
                session.update(customer);
                transaction.commit();
                return true;
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }

    }


    @Override
    public boolean delete(Long id) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Customer deletedCustomer = session.get(Customer.class, id);
            if (deletedCustomer == null) {
                return false;
            } else {
                Transaction transaction = session.beginTransaction();
                session.delete(deletedCustomer);
                return true;
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer get(Long id) {
        Customer customer = null;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            customer = session.get(Customer.class, id);
        } catch (HibernateException | NoResultException hibernateException) {
            hibernateException.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer getByMail(String email) {
        Customer customer = null;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            customer = session.createQuery("SELECT customer from Customer customer where customer.email=: email", Customer.class).setParameter("email", email).getSingleResult();
        } catch (HibernateException | NoResultException hibernateException) {
            hibernateException.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer getByUserName(String username) {
        Customer customer = null;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            customer = session.createQuery("SELECT customer from Customer customer where customer.userName=: username", Customer.class).setParameter("username", username).getSingleResult();
        } catch (HibernateException | NoResultException hibernateException) {
            hibernateException.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("From Customer", Customer.class).list();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return new ArrayList<Customer>();
        }
    }

    @Override
    public boolean resetPassword(String email, String password) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Customer customer = getByMail(email); //get customer by email
            if (customer == null) {
                System.out.println("Customer not found in DB");
                return false;
            }
            //update customer's password
            customer.setPassword(password);
            //update customer's status to activated
            customer.setUserStatus(UserStatus.ACTIVATED);
            session.update(customer);
            session.beginTransaction().commit();
            return true;
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
    }
}
