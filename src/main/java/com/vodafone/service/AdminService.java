package com.vodafone.service;

import com.vodafone.exception.admin.CreateAdminException;
import com.vodafone.exception.admin.GetAdminException;
import com.vodafone.model.Admin;
import com.vodafone.repository.admin.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> getAll() throws GetAdminException {
        Optional<List<Admin>> optionalAdmins = adminRepository.getAll();
        if (optionalAdmins.isPresent())
            return optionalAdmins.get();
        else
            throw new GetAdminException("No admins found");
    }

    public Admin getAdminById(Long id) {
        Optional<Admin> admin = adminRepository.getById(id);
        if (admin.isPresent())
            return admin.get();
        throw new GetAdminException("No admin found with id: " + id);
    }

    public boolean deleteAdmin(Long id) {
        Optional<Admin> admin = adminRepository.getById(id);
        if (admin.isPresent())
            return adminRepository.delete(id);
        throw new GetAdminException("No admin found with id: " + id);
    }

    public boolean createAdmin(Admin admin) {
        Optional<Long> optionalLong = adminRepository.create(admin);
        if (optionalLong.isPresent())
            return true;
        throw new CreateAdminException("Failed to create new admin");
    }

    public boolean updateAdmin(Long id, Admin admin) {
        Optional<Admin> foundAdmin = adminRepository.getById(id);
        if (foundAdmin.isPresent())
            return adminRepository.update(id, admin);
        throw new GetAdminException("No admin found with id: " + id);
    }

    public boolean updatePassword(Long id, String newPassword) {
        Optional<Admin> admin = adminRepository.getById(id);
        if (admin.isPresent())
            return adminRepository.updatePassword(id, newPassword);
        throw new GetAdminException("No admin found with id: " + id);
    }

    public boolean setFirstLoginFlag(Long id) {
        Optional<Admin> admin = adminRepository.getById(id);
        if (admin.isPresent())
            return adminRepository.setFirstLoginFlag(id);
        throw new GetAdminException("No admin found with id: " + id);
    }

    public Admin getAdminByEmail(String email) {
        Optional<Admin> admin = adminRepository.getByEmail(email);
        if (admin.isPresent())
            return admin.get();
        throw new GetAdminException("No admin found with email: " + email);
    }

    public Admin getAdminByUsername(String username) {
        Optional<Admin> admin = adminRepository.getByUsername(username);
        if (admin.isPresent())
            return admin.get();
        throw new GetAdminException("No admin found with email: " + username);
    }
}
