package com.vodafone.Ecommerce.crudRepo;

import org.springframework.data.repository.CrudRepository;
import com.vodafone.Ecommerce.model.Admin;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin,Long> {
   // Iterable<Admin> findAdminsByEmailIgnoreCase(String email);
    Optional<Admin> findAdminByEmailIgnoreCase(String email);

   // Iterable<Admin> findAdminsByUserNameIgnoreCase(String username);

    Optional<Admin> findAdminByUserNameIgnoreCase(String email);

}
