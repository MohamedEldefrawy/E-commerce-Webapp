package com.vodafone.controller;

import com.vodafone.model.*;
import com.vodafone.model.dto.LoginDTO;
import com.vodafone.service.*;
import com.vodafone.validators.LoginValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/")

@AllArgsConstructor
public class UserController {
    private UserService userService;
    private AdminService adminService;
    private CustomerService customerService;
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
            //user is suspended but not customer
            session.invalidate();
            if (!(userService.getUserByEmail(login.getEmail()) instanceof Admin)) {
                Customer customer = customerService.getByMail(login.getEmail());
                if (customer.getUserStatus() == UserStatus.SUSPENDED) { // if user status is suspended after validation
                    if (emailService.sendEmail(customer, EmailType.FORGET_PASSWORD, session))
                        return "redirect:/customer/resetPassword.htm";
                }
            }
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
            //reset attempts
            Customer customer = customerService.get(user.getId());
            customer.setLoginAttempts(3);
            customerService.update(customer.getId(), customer);
            return "redirect:/customer/home.htm";
        } else { //User only logic
            switch (user.getUserStatus()) {
                case SUSPENDED:
                    if (emailService.sendEmail(user, EmailType.FORGET_PASSWORD, session))
                        return "redirect:/customer/resetPassword.htm";
                    break;
                case DEACTIVATED:
                    if (emailService.sendEmail(user, EmailType.ACTIVATION, session))
                        return "redirect:/customer/verify.htm";
                    break;
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

    @GetMapping("error.htm")
    public String showError() {
        return "customer/shared/error";
    }
}
//todo: add logout function
