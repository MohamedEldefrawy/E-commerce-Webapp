package com.vodafone.validators;


import com.vodafone.model.Admin;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@NoArgsConstructor
@Component
public class AdminValidator implements Validator {
    @Autowired
    private AdminService adminService;


    @Override
    public boolean supports(Class<?> paramClass) {
        return CreateAdmin.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        CreateAdmin admin = (CreateAdmin) obj;

        System.out.println("checking");
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