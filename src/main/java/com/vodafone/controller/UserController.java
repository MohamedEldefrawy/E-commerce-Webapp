package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.EmailType;
import com.vodafone.model.User;
import com.vodafone.model.UserStatus;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.model.dto.LoginDTO;
import com.vodafone.service.AdminService;
import com.vodafone.service.HashService;
import com.vodafone.service.SendEmailService;
import com.vodafone.service.UserService;
import com.vodafone.validators.LoginValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/")

@AllArgsConstructor
public class UserController {
    private UserService userService;
    private AdminService adminService;
    private SendEmailService emailService;
    private LoginValidator validator;

    private HashService hashService;

    @GetMapping("login.htm")
    public String login(Model model) {
        model.addAttribute("loginModel", new LoginDTO());
        return "login";
    }

    @PostMapping("login.htm")
    public String login(@Valid @ModelAttribute("loginModel") LoginDTO login, BindingResult bindingResult, HttpSession session) {
        Map<String, Object> model = bindingResult.getModel();
        if (bindingResult.hasErrors()) {
            return "login";
        }
        validator.validate(login, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User user = userService.getUserByEmail(login.getEmail());
        session.setAttribute("email", login.getEmail());
        session.setAttribute("id", user.getId());
        if (user.getUserStatus() == UserStatus.ADMIN) { //Admin-only logic
            Admin admin = adminService.get(user.getId());
            if (admin.isFirstLogin()) {
                if (emailService.sendEmail(user, EmailType.SET_ADMIN_PASSWORD, session))
                    adminService.setFirstLoginFlag(admin.getId()); //set flag to false
                return "redirect:/setAdminPassword.htm";
            } else {
                return "redirect:/admins/home.htm";
            }
        } else if (user.getUserStatus() == UserStatus.ACTIVATED) { //valid credentials customer
            return "redirect:/customer/home.htm";
        } else { //User only logic
            switch (user.getUserStatus()) {
                case SUSPENDED:
                    emailService.sendEmail(user, EmailType.FORGET_PASSWORD, session);
                    return "redirect:/customer/resetPassword.htm";
                case DEACTIVATED:
                    emailService.sendEmail(user, EmailType.ACTIVATION, session);
                    return "redirect:/customer/verify.htm";
                case NOT_REGISTERED:
                    return "redirect:/customer/registration.htm";
            }
        }
        return "login";
    }

    @GetMapping("logout.htm")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
//todo: add logout function
