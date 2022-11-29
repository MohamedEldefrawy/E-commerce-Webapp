package com.vodafone.controller;

import com.vodafone.model.User;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@NoArgsConstructor
@AllArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping("login.htm")
    public String login(String email, String password) {
        return null;
    }

    public String create(User user) {
        return null;
    }

    public String update(Long id, User updatedEntity) {
        return null;
    }

    public String delete(Long id) {
        return null;
    }

    public String get(Long id) {
        return null;
    }

    public String getAll() {
        return null;
    }
}
