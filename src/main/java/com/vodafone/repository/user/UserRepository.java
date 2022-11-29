package com.vodafone.repository.user;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Role;
import com.vodafone.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@AllArgsConstructor
@NoArgsConstructor
public class UserRepository implements IUserRepository {
    HibernateConfig config;

    @Override
    public String login(String email, String password) {
        /*
         * Fetch user using email and password, check his role then redirect to admin-panel or home page
         * Admin-panel: if role == admin
         * home page: if role == customer
         * */
        SessionFactory factory = config.getSessionFactory();
        Session session = factory.openSession();
        Query query = session.createQuery("SELECT DISTINCT user FROM User user where user.email=:email and user.password =:password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        User user = (User) query.uniqueResult();

        session.close();
        if (user.getRole() == Role.Admin) {
            return "redirect:/admin-panel.htm";
        } else
            return "redirect:/home.htm";
    }
}
