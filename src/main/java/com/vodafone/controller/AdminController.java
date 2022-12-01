package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.Product;
import com.vodafone.model.Role;

import com.vodafone.model.User;
import com.vodafone.model.dto.CreateAdmin;
import com.vodafone.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller

@RequestMapping("/admins")

public class AdminController {
    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setPassword("1234");
        admin.setRole(Role.Admin);
        admin.setUserName("admoona");
        adminService.create(admin);
    }


    @GetMapping("/admins.htm")

    public String getAll(Model model) {
        List<Admin> adminList = this.adminService.getAll();
        model.addAttribute("admins", adminList);
        return "viewAllAdmins2";

    }

    @GetMapping("/admins.htm/{id}")
    public Admin get(@PathVariable("id") Long id) {
        return adminService.get(id);
    }

    @DeleteMapping("/admins.htm")
    public String delete(@RequestParam("id") Long id) {
        System.out.println("deleting" + id);
        adminService.delete(id);
        return "redirect:/admins/admins.htm";
    }

    /*@PutMapping("/admins.htm")
    public boolean create(Admin admin) {
        return adminService.create(admin);
    }*/

    @PutMapping("/admins.htm")
    public boolean update(Long id, Admin admin) {
        return adminService.update(id, admin);
    }
    @GetMapping("/createAdmin.htm")
    public String getCreateAdminPage(Model model) {
        model.addAttribute("admin", new CreateAdmin());
        return "createAdmin";

    }
    @PostMapping("/createAdmin.htm")
    public String addUser(@Valid @ModelAttribute("admin") CreateAdmin createAdmin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> model = bindingResult.getModel();
            System.out.println(model);
            return "createAdmin";
        }
        Admin admin = new Admin();
        admin.setUserName(createAdmin.getUsername());
        admin.setRole(Role.Admin);
        admin.setEmail(createAdmin.getEmail());
        admin.setPassword("123456");
        adminService.create(admin);
        return "redirect:/admins/admins.htm";
    }
}
