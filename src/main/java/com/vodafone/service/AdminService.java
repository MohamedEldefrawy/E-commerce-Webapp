package com.vodafone.service;

import com.vodafone.model.Admin;
import com.vodafone.repository.admin.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> findAll(){
        return adminRepository.findAll();
    }

    public Admin findOne(int id){
        return adminRepository.findOne(id);
    }

    public boolean deleteOne(int id){
        return adminRepository.deleteOne(id);
    }

    public boolean save(Admin admin){
        return adminRepository.save(admin);
    }
}
