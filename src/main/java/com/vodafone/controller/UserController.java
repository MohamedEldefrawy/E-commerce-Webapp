package com.vodafone.controller;

import com.vodafone.model.dto.CreateUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping("registration.htm")
    public String registration(Model model) {
        model.addAttribute("user", new CreateUser());
        return "registration";
    }
}
