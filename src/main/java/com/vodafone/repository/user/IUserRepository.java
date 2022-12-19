package com.vodafone.repository.user;

import com.vodafone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}
