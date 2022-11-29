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
    public List<Admin> findAll(){
        return adminService.findAll();
    }
    @GetMapping
    public Admin findOne(@RequestParam("id") int id){
        return adminService.findOne(id);
    }

    @DeleteMapping
    public boolean deleteOne(@RequestParam("id") int id){
        return adminService.deleteOne(id);
    }

    @PutMapping
    public boolean save(Admin admin){
        return adminService.save(admin);
    }
}
