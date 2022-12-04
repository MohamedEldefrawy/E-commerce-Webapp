package com.vodafone.repository.user;

import com.vodafone.model.User;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.model.dto.LoginDTO;
import com.vodafone.repository.Repository;

public interface IUserRepository {
    LoginDTO login(String email, String password);
}
