package com.vodafone.service;

import com.vodafone.model.User;
import com.vodafone.repository.user.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private IUserRepository userRepository;

    public User getUserByEmail(String email){ return userRepository.getByEmail(email); }
}
