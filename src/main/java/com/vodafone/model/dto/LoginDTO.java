package com.vodafone.model.dto;

import com.vodafone.model.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginDTO {
    private boolean isCredentialsValid;
    private LoginResponse response;
}
