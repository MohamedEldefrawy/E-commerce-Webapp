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
@NoArgsConstructor
public class UserService {
    IUserRepository userRepository;

    public LoginDTO login(String email, String password) {
        return userRepository.login(email,password);
    }

    public boolean create(User user) {
        return userRepository.create(user);
    }

    public boolean update(Long id, User updatedEntity) {
        return userRepository.update(id, updatedEntity);
    }

    public boolean delete(Long id) {
        return userRepository.delete(id);
    }

    public User get(Long id) {
        return userRepository.get(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
