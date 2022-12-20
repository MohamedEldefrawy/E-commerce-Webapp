package com.vodafone.validators;


import com.vodafone.model.Admin;
import com.vodafone.model.Customer;
import com.vodafone.model.User;
import com.vodafone.model.UserStatus;
import com.vodafone.model.dto.LoginDTO;
import com.vodafone.service.CustomerService;
import com.vodafone.service.HashService;
import com.vodafone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class LoginValidator implements Validator {
    private UserService userService;

    private HashService hashService;

    private CustomerService customerService; //to handle customer suspending-logic

    @Override
    public boolean supports(Class<?> paramClass) {
        return LoginDTO.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        LoginDTO loginDTO = (LoginDTO) obj;
        User user = userService.getUserByEmail(loginDTO.getEmail());
        if (user == null) {
            errors.rejectValue("password", "invalid", new Object[]{"'password'"},
                    "Incorrect username");
            return;
        }

        if (!hashService.isPasswordValid(loginDTO.getPassword(), user.getPassword())) {
            errors.rejectValue("password", "invalid", new Object[]{"'password'"},
                    "Incorrect password");
            if (!(userService.getUserByEmail(user.getEmail()) instanceof Admin)) {
                //handle suspend logic for customer
                Customer customer = customerService.findCustomerById(user.getId());
                if (customer.getLoginAttempts() > 1) {//decrement attempts
                    customer.setLoginAttempts(customer.getLoginAttempts() - 1);
                    customerService.update(customer.getId(), customer);
                } else {
                    customer.setLoginAttempts(0);
                    customer.setUserStatus(UserStatus.SUSPENDED);
                    customerService.update(customer.getId(), customer);
                }
            }
        }
    }
}