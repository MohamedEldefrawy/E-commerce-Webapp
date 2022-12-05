package com.vodafone.validators;

import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.service.AdminService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class AdminValidator implements Validator {
    private AdminService adminService;

    @Override
    public boolean supports(Class<?> paramClass) {
        return CreateAdmin.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        CreateAdmin admin = (CreateAdmin) obj;

        if(adminService.getByEmail(admin.getEmail())!=null){
            errors.rejectValue("email", "duplicated", new Object[]{"'email'"},
                    "This Email Already Exists");
        }
        if(adminService.getByUsername(admin.getUserName())!=null){
            errors.rejectValue("userName", "duplicated", new Object[]{"'userName'"},
                    "This Username Already Exists");
        }
    }
}