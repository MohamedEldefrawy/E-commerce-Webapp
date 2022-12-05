package com.vodafone.repository.user;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.User;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@AllArgsConstructor
@Repository
public class UserRepository implements IUserRepository {
    private final HibernateConfig hibernateConfig;

    @Override
    public User getByEmail(String email) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            try {
                User user = session.createQuery("SELECT user from User user where user.email=: email", User.class).setParameter("email", email).getSingleResult();
                return user;
            }
            catch (NoResultException e){
                return null;
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean verifyUserCredentials(String email, String password) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            try {
                User user = session.createQuery("SELECT user from User user where user.email=: email and user.password =:password", User.class)
                        .setParameter("email", email)
                        .setParameter("password", password)
                        .getSingleResult();
                return user!=null;
            }
            catch (NoResultException e){
                return false;
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
    }
}
