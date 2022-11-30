package com.vodafone.repository.user;

import com.vodafone.model.User;
import com.vodafone.repository.Repository;

public interface IUserRepository extends Repository<User> {
    User login(String email, String password);
}
