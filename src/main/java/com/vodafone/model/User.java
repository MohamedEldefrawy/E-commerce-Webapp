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
import javax.validation.constraints.Size;

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
    @Size(min = 4, message = "Password can't be less than 4 characters")
    @Size(max = 30, message = "Password exceeded 30 characters")
    String password;
    @Column(nullable = false)
    UserStatus userStatus;
    Role role;
}
