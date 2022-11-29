package com.vodafone.controller;

import com.vodafone.model.Admin;
import com.vodafone.service.AdminService;

import java.util.List;

public class AdminController {
    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    public List<Admin> findAll(){
        return adminService.findAll();
    }

    public Admin findOne(int id){
        return adminService.findOne(id);
    }

    public boolean deleteOne(int id){
        return adminService.deleteOne(id);
    }

    public boolean save(Admin admin){
        return adminService.save(admin);
    }
}
