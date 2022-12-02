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

@Controller
@RequestMapping("/users")
@NoArgsConstructor
@AllArgsConstructor
public class UserController {
    UserService userService;
    AdminService adminService;

    @GetMapping("login.htm")
    public String login(String email, String password) {
        LoginDTO loginDTO = userService.login(email, password);
        if (loginDTO == null) //exception occurred
        {
            System.out.println("Error occurred while logging in....");
        } else {
            Admin admin = (Admin) userService.get(loginDTO.getUserId());
            if (loginDTO.getStatus() == UserStatus.ADMIN && admin.isFirstLogin()) { //checks first-login flag
                //todo: send email with new generated password
                adminService.setFirstLoginFlag(admin.getId()); //set flag to false
                return "redirect:/resetPassword.htm";
            } else if (loginDTO.getStatus() == UserStatus.ADMIN && loginDTO.isCredentialsValid()) {
                //todo: redirect to admin panel
            } else if (loginDTO.getStatus() == UserStatus.ACTIVATED && loginDTO.isCredentialsValid()) {
                return "redirect:/home.htm";
            }
            if ((loginDTO.getStatus() == UserStatus.ADMIN || loginDTO.getStatus() == UserStatus.ACTIVATED) && !loginDTO.isCredentialsValid()) {
                //todo: display 'incorrect email or password is entered' message
            } else if (loginDTO.getStatus() == UserStatus.SUSPENDED) {
                return "redirect:/resetPassword.htm";
            } else if (loginDTO.getStatus() == UserStatus.DEACTIVATED) {
                //todo: redirect to verify email page
            } else if (loginDTO.getStatus() == UserStatus.NOT_REGISTERED) {
                return "redirect:/registration.htm";
            }
        }
        return null;
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
