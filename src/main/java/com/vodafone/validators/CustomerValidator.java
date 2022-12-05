package com.vodafone.validators;

import com.vodafone.model.Customer;
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
    @Autowired
    private CustomerService customerService;


    @Override
    public boolean supports(Class<?> paramClass) {
        return Customer.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Customer customer = (Customer) obj;

        System.out.println("checking");
        if(customerService.getByMail(customer.getEmail())!=null){
            errors.rejectValue("email", "duplicated", new Object[]{"'email'"},
                    "This Email Already Exists");
        }
        if(customerService.getByUserName(customer.getUserName())!=null){
            errors.rejectValue("userName", "duplicated", new Object[]{"'userName'"},
                    "This Username Already Exists");
        }


    }

}
