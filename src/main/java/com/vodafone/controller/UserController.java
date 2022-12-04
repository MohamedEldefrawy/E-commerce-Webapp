package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.UserStatus;
import com.vodafone.model.User;
import com.vodafone.model.dto.LoginDTO;
import com.vodafone.service.AdminService;
import com.vodafone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
@NoArgsConstructor
@AllArgsConstructor
public class UserController {
    UserService userService;
    AdminService adminService;

    @GetMapping("login.htm")
    public String login(String email, String password, HttpSession session) {
        LoginDTO loginDTO = userService.login(email, password);
        session.setAttribute("email", email);
        session.setAttribute("password", password);
        if (loginDTO == null) //exception occurred
        {
            System.out.println("Error occurred while logging in....");
        } else {
            session.setAttribute("id", loginDTO.getUserId());
            if (loginDTO.getStatus() == UserStatus.ADMIN) { //Admin-only logic
                Admin admin = (Admin) userService.get(loginDTO.getUserId());
                if (admin.isFirstLogin()) {
                    //todo: send email with new generated password
                    adminService.setFirstLoginFlag(admin.getId()); //set flag to false
                    return "redirect:/setAdminPassword.htm";
                }
                if (loginDTO.isCredentialsValid()) {
                    return "redirect:/products.htm";
                }
            } else if (loginDTO.getStatus() == UserStatus.ACTIVATED && loginDTO.isCredentialsValid()) { //valid credentials customer
                return "redirect:/home.htm";
            }
            if ((loginDTO.getStatus() == UserStatus.ADMIN || loginDTO.getStatus() == UserStatus.ACTIVATED) && !loginDTO.isCredentialsValid()) {
                //todo: display 'incorrect email or password is entered' message
                System.out.println("Wrong Credentials!");
            } else { //User only logic
                switch (loginDTO.getStatus()) {
                    case SUSPENDED:
                        return "redirect:/resetPassword.htm";
                    case DEACTIVATED:
                        return "redirect:/verify.htm";
                    case NOT_REGISTERED:
                        return "redirect:/registration.htm";
                }
            }
        }
        return "login";
    }

    @PostMapping
    public String create(@RequestBody User user) {
        userService.create(user);
        return null;
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id, @RequestBody User updatedEntity) {
        userService.update(id, updatedEntity);
        return null;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return null;
    }

    @GetMapping("{id}")
    public String get(@PathVariable Long id) {
        userService.get(id);
        return null;
    }

    @GetMapping
    public String getAll() {
        userService.getAll();
        return null;
    }
}
