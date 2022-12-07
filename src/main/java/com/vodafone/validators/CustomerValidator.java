package com.vodafone.validators;

import com.vodafone.model.Customer;
import com.vodafone.service.AdminService;
import com.vodafone.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class CustomerValidator implements Validator {
    private CustomerService customerService;
    private AdminService adminService;



    @Override
    public boolean supports(Class<?> paramClass) {
        return Customer.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Customer customer = (Customer) obj;
        String emailRegEx = "^\\w+([\\.]?\\w+){3,}@\\w{4,8}([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        if(customer.getEmail().length()<5){
            errors.rejectValue("email", "invalid", new Object[]{"'email'"},
                    "Email address is not valid");
        }
        else {
            String lastFiveChars = customer.getEmail().substring(customer.getEmail().length() - 5);
            if (!customer.getEmail().matches(emailRegEx)) {
                errors.rejectValue("email", "invalid", new Object[]{"'email'"},
                        "Email address is not valid");
            }
            else if (!lastFiveChars.startsWith("co")) {
                String lastThreeChars = lastFiveChars.substring(lastFiveChars.length() - 3);
                System.out.println(lastThreeChars);
                if (!lastThreeChars.equals("com")  && !lastThreeChars.equals("org")) {
                    errors.rejectValue("email", "invalid", new Object[]{"'email'"},
                            "Email address is not valid");
                }
            }
        }


        if(customerService.getByMail(customer.getEmail())!=null ||
                adminService.getByEmail(customer.getEmail())!=null){
            errors.rejectValue("email", "duplicated", new Object[]{"'email'"},
                    "This Email Already Exists");
        }
        if(customerService.getByUserName(customer.getUserName())!=null ||
                adminService.getByUsername(customer.getUserName())!=null){
            errors.rejectValue("userName", "duplicated", new Object[]{"'userName'"},
                    "This Username Already Exists");
        }

    }

}
