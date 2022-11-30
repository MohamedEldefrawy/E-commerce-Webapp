package com.vodafone.model.dto;

import com.vodafone.model.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser {
    @NotNull
    @NotBlank
    private String userName;
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 8, message = "Password can't be less than 8 characters")
    @Size(max = 30, message = "Password exceeded 30 characters")
    private String password;
    @NotNull
    @NotBlank
    private Role role;
}
