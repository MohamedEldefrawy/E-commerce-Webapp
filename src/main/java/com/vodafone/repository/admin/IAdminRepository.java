package com.vodafone.repository.admin;

import com.vodafone.model.Admin;
import com.vodafone.repository.Repository;


public interface IAdminRepository extends Repository<Long, Admin> {
    void setFirstLoginFlag(Long id);

    Admin getByEmail(String email);
}
