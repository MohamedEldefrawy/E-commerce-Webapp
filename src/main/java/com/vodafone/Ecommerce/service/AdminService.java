package com.vodafone.Ecommerce.service;

import com.vodafone.Ecommerce.exception.admin.CreateAdminException;
import com.vodafone.Ecommerce.exception.admin.GetAdminException;
import com.vodafone.Ecommerce.model.Admin;
import com.vodafone.Ecommerce.crudRepo.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> getAll() throws GetAdminException {
        Iterable<Admin> optionalAdmins = adminRepository.findAll();
        if(!optionalAdmins.iterator().hasNext())
            throw new GetAdminException("No admins found");
        else{
            //convert iterable to List
            return StreamSupport.stream(optionalAdmins.spliterator(), false)
                    .collect(Collectors.toList());
        }
    }

    public Admin getAdminById(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent())
            return admin.get();
        throw new GetAdminException("No admin found with id: " + id);
    }

    public boolean deleteAdmin(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            adminRepository.deleteById(id);
            return !adminRepository.findById(id).isPresent();
        }
        else {
            throw new GetAdminException("No admin found with id: " + id);
        }
    }

    public Admin createAdmin(Admin admin) {
        if(admin.getUserName()==null)
            throw new CreateAdminException("Username cannot be null");
        if(admin.getEmail()==null)
            throw new CreateAdminException("Email cannot be null");
        if(admin.getPassword()==null)
            admin.setPassword(getAlphaNumericString(8));
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long id, Admin admin) {
        Optional<Admin> foundAdmin = adminRepository.findById(id);
        if (foundAdmin.isPresent())
            return adminRepository.save(admin);
        throw new GetAdminException("No admin found with id: " + id);
    }

    public Admin updatePassword(Long id, String newPassword) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setPassword(newPassword);
            return adminRepository.save(admin);
        }
        throw new GetAdminException("No admin found with id: " + id);
    }

    public Admin setFirstLoginFlag(Long id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setFirstLogin(true);
            return adminRepository.save(admin);
        }
        throw new GetAdminException("No admin found with id: " + id);
    }

    public Admin getAdminByEmail(String email) {
        Optional<Admin> admin = adminRepository.findAdminByEmailIgnoreCase(email);
        if (admin.isPresent())
            return admin.get();
        throw new GetAdminException("No admin found with email: " + email);
    }

    public Admin getAdminByUsername(String username) {
        Optional<Admin> admin = adminRepository.findAdminByUserNameIgnoreCase(username);
        if (admin.isPresent())
            return admin.get();
        throw new GetAdminException("No admin found with email: " + username);
    }

    private String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz_&%";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
}
