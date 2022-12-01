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
public class CreateAdmin {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String email;


}
