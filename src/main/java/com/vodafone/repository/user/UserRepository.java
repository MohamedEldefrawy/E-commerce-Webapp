package com.vodafone.repository.user;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.*;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.model.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Repository
public class UserRepository implements IUserRepository {
    HibernateConfig config;

    @Override
    public LoginDTO login(String email, String password) {
        /*
         * Make use of LoginDTO to determine login status and validation @ controller
         * Customer:
         *   - Determine if user is suspended -> LoginDTO(T/F, suspended)
         *   - Determine if user is deactivated -> LoginDTO(T/F, deactivated)
         *   - Determine if user is activated  -> LoginDTO(T/F, activated)
         *   - Determine if user is Admin  -> LoginDTO(T/F, Admin)
         *   --------------------------------------------------------
         *   when to accept and redirect user to its home [AdminPanel, home]
         *       1. LoginStatus: Admin, isCredentialsValid: true
         *       2. LoginStatus: Activated, isCredentialsValid: true
         *   --------------------------------------------------------
         *   Errors and redirect:
         *      1. LoginStatus: Admin, isCredentialsValid: false -> display 'incorrect username or password'
         *      2. LoginStatus: Activated, isCredentialsValid: false -> display 'incorrect username or password'
         *      3. LoginStatus: Suspended -> redirect to reset password view
         *      4. LoginStatus: Deactivated -> redirect to activate account view
         *      5. LoginStatus: Not_Registered -> redirect to register page
         * */
        User user;
        LoginDTO loginDTO = new LoginDTO();
        try (Session session = config.getSessionFactory().openSession()) {
            //add queries criteria
            HashMap<String, String> queries = new HashMap<>();
            queries.put("email", email);
            user = session.get(User.class, queries);
            if (user != null) { //user's email exist
                loginDTO.setUserId(user.getId()); //set user id to DTO
                if (user.getRole() == Role.Admin) {
                    //set login response to admin
                    loginDTO.setStatus(UserStatus.ADMIN);
                } else if (user.getRole() == Role.Customer) {
                    //check customer status and login credentials
                    Customer customer = (Customer) user;
                    //set status
                    switch (customer.getUserStatus()) {
                        case ACTIVATED:
                            loginDTO.setStatus(UserStatus.ACTIVATED);
                            break;
                        case DEACTIVATED:
                            loginDTO.setStatus(UserStatus.DEACTIVATED);
                            break;
                        case SUSPENDED:
                            loginDTO.setStatus(UserStatus.SUSPENDED);
                            break;
                    }
                }
                //check credentials -> if email and password exists then return true
                queries.put("password", password);
                boolean isCredentialValid = session.get(User.class, queries) != null;
                System.out.println("Is user's credentials valid?: " + isCredentialValid);
                loginDTO.setCredentialsValid(isCredentialValid);
            } else { //user not registered -> redirect to register.jsp
                return new LoginDTO(false, UserStatus.NOT_REGISTERED, null);
            }
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
        return loginDTO;
    }
    public User getByMail(String email) {
        User user = null;
        try (Session session = config.getSessionFactory().openSession()) {
            user = session.createQuery("SELECT user from User user where user.email=: email", User.class).setParameter("email", email).getSingleResult();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return user;
    }
    public User getByMailAndPassword(String email,String password) {
        User user = null;
        try (Session session = config.getSessionFactory().openSession()) {
            user = session.createQuery("SELECT user from User user where user.email=: email and user.password=: password", User.class)
                    .setParameter("email", email)
                    .setParameter("password",password).getSingleResult();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
        }
        return user;
    }
}
