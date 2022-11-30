package com.vodafone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {

    @Column(nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVATED;

}
