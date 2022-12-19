//package com.vodafone.Ecommerce.validators;
//
//import com.vodafone.Ecommerce.model.Customer;
//import com.vodafone.Ecommerce.service.AdminService;
//import com.vodafone.Ecommerce.service.CustomerService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//
//import org.springframework.validation.Validator;
//
//@AllArgsConstructor
//@Component
//public class CustomerValidator implements Validator {
//    private CustomerService customerService;
//    private AdminService adminService;
//
//
//    @Override
//    public boolean supports(Class<?> paramClass) {
//        return Customer.class.equals(paramClass);
//    }
//
//    @Override
//    public void validate(Object obj, Errors errors) {
//        Customer customer = (Customer) obj;
//        //todo: test remove below conditions, just let .matches(regex)
//        String emailRegEx = "^\\w+([\\.]?\\w+){3,}@\\w{4,8}([\\.-]?\\w+)*(\\.\\w{2,3})+$";
//        if (!customer.getEmail().matches(emailRegEx)) {
//            errors.rejectValue("email", "invalid", new Object[]{"'email'"},
//                    "Email address is not valid");
//        }
//
//        if (customerService.getByMail(customer.getEmail()) != null ||
//                adminService.getAdminByEmail(customer.getEmail()) != null) {
//            errors.rejectValue("email", "duplicated", new Object[]{"'email'"},
//                    "This Email Already Exists");
//        }
//        if (customerService.getByUserName(customer.getUserName()) != null ||
//                adminService.getAdminByUsername(customer.getUserName()) != null) {
//            errors.rejectValue("userName", "duplicated", new Object[]{"'userName'"},
//                    "This Username Already Exists");
//        }
//
//    }
//
//}
