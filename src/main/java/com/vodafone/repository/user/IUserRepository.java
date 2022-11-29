package com.vodafone.repository.user;

import com.vodafone.model.User;

public interface IUserRepository {
    String login(String email, String password);
    User validateRegister(String email, String password);
}
