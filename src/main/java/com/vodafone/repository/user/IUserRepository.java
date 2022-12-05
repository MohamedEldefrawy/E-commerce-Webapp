package com.vodafone.repository.user;

import com.vodafone.model.User;

public interface IUserRepository {

    User getByEmail(String email);

    boolean verifyUserCredentials(String email, String password);
}
