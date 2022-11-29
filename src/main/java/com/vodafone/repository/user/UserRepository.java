package com.vodafone.repository.user;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Role;
import com.vodafone.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.HashMap;

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
        User user = validateRegister(email, password);
        if (user == null) {
            //todo: display error message
            return "login"; //reject request, user is not registered
        }
        if (user.getRole() == Role.Admin) {
            return "redirect:/admin-panel.htm";
        } else
            return "redirect:/home.htm";
    }

    @Override
    public User validateRegister(String email, String password) {
        SessionFactory factory = config.getSessionFactory();
        User user;
        try (Session session = factory.openSession()) {
            //add queries criteria
            HashMap<String, String> queries = new HashMap<>();
            queries.put("email", email);
            queries.put("password", password);
            user = session.get(User.class, queries);
            session.close();
        }catch (HibernateException e){
            e.printStackTrace(); //todo: fix exception message
            return null;
        }
        return user;
    }
}
