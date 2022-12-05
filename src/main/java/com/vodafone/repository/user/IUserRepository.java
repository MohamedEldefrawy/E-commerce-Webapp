package com.vodafone.repository.user;

import com.vodafone.model.User;
import com.vodafone.model.dto.LoginDTO;

public interface IUserRepository {
    LoginDTO login(String email, String password);

    User getByEmail(String email);

    boolean verifyUserCredentials(String email, String password);
}
