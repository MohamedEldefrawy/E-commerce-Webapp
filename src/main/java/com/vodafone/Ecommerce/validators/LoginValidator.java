//package com.vodafone.Ecommerce.validators;
//
//
//import com.vodafone.Ecommerce.model.Admin;
//import com.vodafone.Ecommerce.model.Customer;
//import com.vodafone.Ecommerce.model.User;
//import com.vodafone.Ecommerce.model.UserStatus;
//import com.vodafone.Ecommerce.model.dto.LoginDTO;
//import com.vodafone.Ecommerce.service.UserService;
//import com.vodafone.Ecommerce.service.CustomerService;
//import com.vodafone.Ecommerce.service.HashService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import javax.servlet.http.HttpSession;
//
//@AllArgsConstructor
//@Component
//public class LoginValidator implements Validator {
//    private UserService userService;
//
//    private HashService hashService;
//
//    private CustomerService customerService; //to handle customer suspending-logic
//
//    @Override
//    public boolean supports(Class<?> paramClass) {
//        return LoginDTO.class.equals(paramClass);
//    }
//
//    @Override
//    public void validate(Object obj, Errors errors) {
//        LoginDTO loginDTO = (LoginDTO) obj;
//        User user = userService.getUserByEmail(loginDTO.getEmail());
//        if (user == null) {
//            errors.rejectValue("password", "invalid", new Object[]{"'password'"},
//                    "Incorrect username");
//            return;
//        }
//
//        if (!hashService.isPasswordValid(loginDTO.getPassword(), user.getPassword())) {
//            errors.rejectValue("password", "invalid", new Object[]{"'password'"},
//                    "Incorrect password");
//            if (!(userService.getUserByEmail(user.getEmail()) instanceof Admin)) {
//                //handle suspend logic for customer
//                Customer customer = customerService.getById(user.getId());
//                if (customer.getLoginAttempts() > 1) {//decrement attempts
//                    customer.setLoginAttempts(customer.getLoginAttempts() - 1);
//                    customerService.update(customer.getId(), customer);
//                } else {
//                    customer.setLoginAttempts(0);
//                    customer.setUserStatus(UserStatus.SUSPENDED);
//                    customerService.update(customer.getId(), customer);
//                }
//            }
//        }
//    }
//}