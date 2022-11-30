package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Admin> getAll() {
        return adminService.getAll();
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
