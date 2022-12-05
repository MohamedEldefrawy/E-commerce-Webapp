package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.EmailType;
import com.vodafone.model.User;
import com.vodafone.model.UserStatus;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.service.AdminService;
import com.vodafone.service.SendEmailService;
import com.vodafone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private AdminService adminService;

    private SendEmailService emailService;

    @GetMapping("login.htm")
    public String login(Model model) {
        model.addAttribute("loginModel", new CreateUser());
        return "login";
    }

    @PostMapping("login.htm")
    public String login(@Valid @ModelAttribute("loginModel") CreateUser createUser, HttpSession session) {
        User user = userService.getUserByEmail(createUser.getEmail());
        boolean isCredentialsValid = userService.verifyUserCredentials(createUser.getEmail(), createUser.getPassword());
        session.setAttribute("email", createUser.getEmail());
        if (user == null) //exception occurred
        {
            System.out.println("Error occurred while logging in....");
        } else {
            session.setAttribute("id", user.getId());
            if (user.getUserStatus() == UserStatus.ADMIN) { //Admin-only logic
                Admin admin = adminService.get(user.getId());
                if (admin.isFirstLogin()) {
                    emailService.sendEmail(user, EmailType.SET_ADMIN_PASSWORD, session);
                    adminService.setFirstLoginFlag(admin.getId()); //set flag to false
                    return "redirect:/setAdminPassword.htm";
                }
                if (isCredentialsValid) {
                    return "redirect:/products.htm";
                }
            } else if (user.getUserStatus() == UserStatus.ACTIVATED && isCredentialsValid) { //valid credentials customer
                return "redirect:/home.htm";
            }
            if ((user.getUserStatus() == UserStatus.ADMIN || user.getUserStatus() == UserStatus.ACTIVATED) && !isCredentialsValid) {
                //todo: display 'incorrect email or password is entered' message
                System.out.println("Wrong Credentials!");
            } else { //User only logic
                switch (user.getUserStatus()) {
                    case SUSPENDED:
                        return "redirect:/customer/resetPassword.htm";
                    case DEACTIVATED:
                        return "redirect:/customer/verify.htm";
                    case NOT_REGISTERED:
                        return "redirect:/customer/registration.htm";
                }
            }
        }
        return "login";
    }
}
