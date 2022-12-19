package com.vodafone.Ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "admins")
public class Admin extends User {
    public Admin() {
        userStatus = UserStatus.ADMIN;
    }

    @Getter
    @Setter
    private boolean firstLogin;

}
