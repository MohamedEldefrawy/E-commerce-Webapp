package com.vodafone.repository.admin;

import com.vodafone.model.Admin;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findAdminByEmailIgnoreCase(String email);

    Optional<Admin> findAdminByUserNameIgnoreCase(String email);

}
