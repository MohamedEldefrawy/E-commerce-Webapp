package com.vodafone.controller;

import com.vodafone.model.User;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@NoArgsConstructor
@AllArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping("login.htm")
    public String login(String email, String password) {
        userService.login(email, password);
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
