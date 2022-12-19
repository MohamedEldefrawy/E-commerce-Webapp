//package com.vodafone.Ecommerce.validators;
//
//import com.vodafone.Ecommerce.model.Admin;
//import com.vodafone.Ecommerce.model.Customer;
//import com.vodafone.Ecommerce.model.UserStatus;
//import com.vodafone.Ecommerce.service.AdminService;
//import com.vodafone.Ecommerce.service.CustomerService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpSession;
//
//@Component
//@AllArgsConstructor
//public class UserAuthorizer {
//
//    AdminService adminService;
//    CustomerService customerService;
//    public boolean authorizeAdmin(HttpSession session){
//        if(session.getAttribute("id")==null){
//            return false;
//        }
//        Long id = (long) session.getAttribute("id");
//        Admin admin = adminService.getAdminById(id);
//        return admin!=null;
//    }
//    public boolean customerExists(HttpSession session){
//        //user isn't logged in
//        if(session.getAttribute("email")==null)
//            return false;
//        Customer customer = customerService.getByMail((String) session.getAttribute("email"));
//        //user is not a customer
//        if(customer==null)
//            return false;
//        //customer account isn't activated
//        return customer.getUserStatus() != UserStatus.NOT_REGISTERED;
//    }
//    public boolean isActivatedCustomer(HttpSession session){
//        if(session.getAttribute("id")==null)
//            return false;
//        Long id = (long) session.getAttribute("id");
//        Customer customer = customerService.getById(id);
//        //user is not a customer
//        if(customer==null)
//            return false;
//        //customer account isn't activated
//        return customer.getUserStatus() == UserStatus.ACTIVATED;
//    }
//}
