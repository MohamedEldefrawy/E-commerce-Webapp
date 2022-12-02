package com.vodafone.model.dto;

import com.vodafone.model.UserStatus;
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
    private UserStatus status;
    private Long userId;
}
