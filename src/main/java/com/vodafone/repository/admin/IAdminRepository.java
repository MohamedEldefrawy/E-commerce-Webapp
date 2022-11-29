package com.vodafone.repository.admin;

import com.vodafone.model.Admin;
import com.vodafone.repository.Repository;


import java.util.List;


public interface IAdminRepository extends Repository {

    public List<Admin> findAll();

    public Admin findOne(int id);

    public boolean deleteOne(int id);

    public boolean save(Admin admin);
}
