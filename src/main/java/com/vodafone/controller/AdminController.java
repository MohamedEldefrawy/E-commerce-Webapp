package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.model.Product;
import com.vodafone.model.Role;
import com.vodafone.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admins.htm")
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

    @GetMapping()
    public String getAll(Model model) {
        List<Admin> adminList = this.adminService.getAll();
        model.addAttribute("admins", adminList);
        return "viewAllAdmins";

    }

    @GetMapping("{id}")
    public Admin get(@PathVariable("id") Long id) {
        return adminService.get(id);
    }

    @DeleteMapping
    public boolean delete(@RequestParam("id") Long id) {
        return adminService.delete(id);
    }

    @PutMapping
    public boolean create(Admin admin) {
        return adminService.create(admin);
    }

    @PostMapping
    public boolean update(Long id, Admin admin) {
        return adminService.update(id, admin);
    }
}
