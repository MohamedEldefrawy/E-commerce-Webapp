package com.vodafone.service;

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

    public Admin get(Long id) {
        return adminRepository.getById(id).get();
    }

    public boolean delete(Long id) {
        return adminRepository.delete(id);
    }

    public boolean create(Admin admin) {
        return adminRepository.create(admin).isPresent();
    }

    public boolean update(Long id, Admin admin) {
        return adminRepository.update(id, admin);
    }

    public boolean updatePassword(Long id, String newPassword) {
        return adminRepository.updatePassword(id, newPassword);
    }

    public void setFirstLoginFlag(Long id) {
        adminRepository.setFirstLoginFlag(id);
    }

    public Admin getByEmail(String email) {
        return adminRepository.getByEmail(email);
    }

    public Admin getByUsername(String username) {
        return adminRepository.getByUsername(username);
    }
}
