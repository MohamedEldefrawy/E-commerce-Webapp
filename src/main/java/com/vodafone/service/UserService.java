package com.vodafone.service;

import com.vodafone.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {
    UserRepository userRepository;
    public String login(String email, String password) {
        return userRepository.login(email,password);
    }
}
