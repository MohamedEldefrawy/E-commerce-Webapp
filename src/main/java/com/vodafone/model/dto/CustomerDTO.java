package com.vodafone.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @Min(100000)
    @Max(999999)
    @NotNull()
    private Integer activationCode;
}
