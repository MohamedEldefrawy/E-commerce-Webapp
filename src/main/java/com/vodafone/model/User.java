package com.vodafone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull @NotBlank
    String userName;
    @Column(unique = true)
    @Email
    @NotNull @NotBlank
    String email;
    @Column(nullable = false)
    @NotNull @NotBlank
    String password;
    @Column(nullable = false)
    UserStatus userStatus;
    Role role;
}
