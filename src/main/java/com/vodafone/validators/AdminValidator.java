package com.vodafone.validators;

import com.vodafone.model.Admin;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.service.AdminService;
import com.vodafone.service.CustomerService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class AdminValidator implements Validator {
    private AdminService adminService;
    private CustomerService customerService;

    @Override
    public boolean supports(Class<?> paramClass) {
        return CreateAdmin.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        CreateAdmin admin = (CreateAdmin) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "required");
        if (errors.hasErrors()) {
            return;
        }
        String emailRegEx = "^\\w+([\\.]?\\w+){3,}@\\w{4,8}([\\.-]?\\w+)*(\\.\\w{2,3})+$";
        if (!admin.getEmail().matches(emailRegEx)) {
            errors.rejectValue("email", "invalid", new Object[]{"'email'"},
                    "Email address is not valid");
        }
        if (errors.hasErrors()) {
            return;
        }
        if (admin.getId() <= 0) {
            if (adminService.getByEmail(admin.getEmail()) != null ||
                    customerService.getByMail(admin.getEmail()) != null) {
                errors.rejectValue("email", "duplicated", new Object[]{"'email'"},
                        "This Email Already Exists");
            }
            if (adminService.getByUsername(admin.getUserName()) != null ||
                    customerService.getByUserName(admin.getUserName()) != null) {
                errors.rejectValue("userName", "duplicated", new Object[]{"'userName'"},
                        "This Username Already Exists");
            }
        } else {
            Admin toBeUpdated = adminService.get(Long.valueOf(admin.getId()));
            //check that email field was updated
            if (!toBeUpdated.getEmail().equals(admin.getEmail())) {
                //check updated email is not duplicated
                if (adminService.getByEmail(admin.getEmail()) != null ||
                        customerService.getByMail(admin.getEmail()) != null) {
                    errors.rejectValue("email", "duplicated", new Object[]{"'email'"},
                            "This Email Already Exists");
                }
            }
            if (!toBeUpdated.getUserName().equals(admin.getUserName())) {
                if (adminService.getByUsername(admin.getUserName()) != null ||
                        customerService.getByUserName(admin.getUserName()) != null) {
                    errors.rejectValue("userName", "duplicated", new Object[]{"'userName'"},
                            "This Username Already Exists");
                }
            }
        }
    }
}