package com.vodafone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {

    @Column(nullable = false)
    @Getter
    private UserStatus userStatus = UserStatus.ACTIVATED;

    @Column
    @Getter
    @Setter
    private boolean firstLogin;

}
