package com.vodafone.repository.user;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.*;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.HashMap;

@AllArgsConstructor
@Repository
public class UserRepository implements IUserRepository {
    private final HibernateConfig hibernateConfig;

    @Override
    public User getByEmail(String email) {
        User user;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            user = session.createQuery("SELECT user from User user where user.email=: email", User.class).setParameter("email", email).getSingleResult();
        } catch (HibernateException | NoResultException hibernateException) {
            hibernateException.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public boolean verifyUserCredentials(String email, String password) {
        boolean isValid = false;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            isValid = session.createQuery("SELECT user from User user where user.email=: email and user.password =:password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult() != null;
        } catch (HibernateException | NoResultException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
        return isValid;
    }


}
