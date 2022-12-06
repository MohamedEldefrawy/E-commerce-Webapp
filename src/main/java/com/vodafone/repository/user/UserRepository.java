package com.vodafone.repository.user;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.User;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@AllArgsConstructor
@Repository
public class UserRepository implements IUserRepository {
    private final HibernateConfig hibernateConfig;

    @Override
    public User getByEmail(String email) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            try {
                List<User> users = session.createQuery("SELECT user from User user where user.email=: email", User.class).setParameter("email", email).list();
                if (users.isEmpty()) throw new NoResultException("No user found");
                return users.get(0);
            } catch (NoResultException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return null;
        }
    }
}
