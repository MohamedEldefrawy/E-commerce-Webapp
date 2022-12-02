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

    public List<Admin> getAll(){
        return adminRepository.getAll();
    }

    public Admin get(Long id){
        return adminRepository.get(id);
    }

    public boolean delete(Long id){
        return adminRepository.delete(id);
    }

    public boolean create(Admin admin){
        return adminRepository.create(admin);
    }

    public boolean update(Long id, Admin admin){
        return adminRepository.update(id,admin);
    }

    public boolean updatePassword(Long id, String newPassword){return adminRepository.updatePassword(id,newPassword);}

    public void setFirstLoginFlag(Long id){ adminRepository.setFirstLoginFlag(id); }
}
