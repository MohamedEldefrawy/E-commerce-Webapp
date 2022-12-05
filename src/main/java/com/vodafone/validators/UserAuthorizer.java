package com.vodafone.validators;

import com.vodafone.service.AdminService;
import com.vodafone.model.Admin;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@AllArgsConstructor
public class UserAuthorizer {

    AdminService adminService;
    public boolean authorizeAdmin(HttpSession session){
        if(session.getAttribute("id")==null){
            return false;
        }
        Long id = (long) session.getAttribute("id");
        Admin admin = adminService.get(id);
        return admin!=null;
    }
}
