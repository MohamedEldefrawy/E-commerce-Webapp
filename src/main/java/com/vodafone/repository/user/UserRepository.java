package com.vodafone.repository.user;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Cart;
import com.vodafone.model.Role;
import com.vodafone.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Repository
public class UserRepository implements IUserRepository {
    HibernateConfig config;

    @Override
    public User login(String email, String password) {
        User user;
        try (Session session = config.getSessionFactory().openSession()) {
            //add queries criteria
            HashMap<String, String> queries = new HashMap<>();
            queries.put("email", email);
            queries.put("password", password);
            user = session.get(User.class, queries);
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace(); //todo: fix exception message
            return null;
        }
        return user;
    }

    @Override
    public boolean create(User entity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace(); //todo: fix exception message
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Long id, User updatedEntity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            updatedEntity.setId(id);
            session.update(updatedEntity);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace(); //todo: fix exception message
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(get(id));
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace(); //todo: fix exception message
            return false;
        }
        return true;
    }

    @Override
    public User get(Long id) {
        User user = null;
        try (Session session = config.getSessionFactory().openSession()) {
            user = session.get(User.class, id);
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            users = session.createQuery("SELECT DISTINCT user From User user", User.class).getResultList();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return users;
        }
        return users;
    }
}
