package com.vodafone.repository.admin;

import com.vodafone.model.Admin;
import com.vodafone.repository.Repository;
import java.util.Optional;


public interface IAdminRepository extends Repository<Long, Admin> {
    boolean setFirstLoginFlag(Long id);

    Optional<Admin> getByEmail(String email);

    Optional<Admin> getByUsername(String username);
}
