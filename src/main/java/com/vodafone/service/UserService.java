package com.vodafone.service;

import com.vodafone.model.User;
import com.vodafone.model.dto.CreateUser;
import com.vodafone.model.dto.LoginDTO;
import com.vodafone.repository.user.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private IUserRepository userRepository;

    public LoginDTO login(String email, String password) {
        return userRepository.login(email,password);
    }

    public User getUserByEmail(String email){ return userRepository.getByEmail(email); }
    public boolean verifyUserCredentials(String email,String password){ return userRepository.verifyUserCredentials(email,password); }
}
