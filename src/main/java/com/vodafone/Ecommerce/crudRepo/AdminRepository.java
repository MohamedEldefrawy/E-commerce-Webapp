package com.vodafone.Ecommerce.crudRepo;

import org.springframework.data.repository.CrudRepository;
import com.vodafone.Ecommerce.model.Admin;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin,Long> {
    Optional<Admin> findAdminByEmailIgnoreCase(String email);

    Optional<Admin> findAdminByUserNameIgnoreCase(String email);

}
