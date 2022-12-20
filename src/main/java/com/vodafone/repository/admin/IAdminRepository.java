package com.vodafone.repository.admin;

import com.vodafone.model.Admin;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface IAdminRepository extends CrudRepository<Admin,Long> {
    Optional<Admin> findAdminByEmailIgnoreCase(String email);

    Optional<Admin> findAdminByUserNameIgnoreCase(String email);

}
