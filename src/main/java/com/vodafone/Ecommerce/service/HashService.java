package com.vodafone.Ecommerce.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class HashService {
    int salt = new Random().nextInt(10);
    private final Argon2PasswordEncoder encoder;

    public HashService() {
        encoder = new Argon2PasswordEncoder(salt, 16, 1, 2 * 1024, 2);
    }

    public String encryptPassword(String password, String email) {
        salt += email.length();
        return encoder.encode(password);
    }

    public boolean isPasswordValid(String enteredPassword, String encryptedPassword) {
        return encoder.matches(enteredPassword, encryptedPassword);
    }
}
