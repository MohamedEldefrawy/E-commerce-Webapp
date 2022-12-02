package com.vodafone.controller;

import com.vodafone.model.LoginResponse;
import com.vodafone.model.User;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.model.dto.LoginDTO;
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
        LoginDTO loginDTO = userService.login(email, password);
        if(loginDTO == null) //exception occurred
        {
            System.out.println("Error occurred while logging in....");
        }else {
            if(loginDTO.getResponse() == LoginResponse.ADMIN && loginDTO.isCredentialsValid()) {
                //todo: redirect to admin panel
            }else if(loginDTO.getResponse() == LoginResponse.ACTIVATED && loginDTO.isCredentialsValid()){
                return "redirect:/home.htm";
            }else if(loginDTO.getResponse() == LoginResponse.SUSPENDED){
                return "redirect:/resetPassword.htm";
            }else if(loginDTO.getResponse() == LoginResponse.DEACTIVATED){
                //todo: redirect to verify email page
            }else if(loginDTO.getResponse() == LoginResponse.NOT_REGISTERED){
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
