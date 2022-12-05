package com.vodafone.model.dto;

import com.vodafone.model.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdmin {
    private int id;
    @NotNull
    @NotBlank
    private String userName;
    @NotNull
    @NotBlank
    @Email
    private String email;


}
