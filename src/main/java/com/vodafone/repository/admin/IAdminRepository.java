package com.vodafone.repository.admin;

import com.vodafone.model.Admin;
import com.vodafone.repository.Repository;


import java.util.List;


public interface IAdminRepository extends Repository<Admin> {
    void setFirstLoginFlag(Long id);
}
