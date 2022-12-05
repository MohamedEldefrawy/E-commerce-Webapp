package com.vodafone.validators;


import com.vodafone.model.User;
import com.vodafone.model.dto.LoginDTO;
import com.vodafone.service.HashService;
import com.vodafone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class LoginValidator implements Validator {
    private UserService userService;

    private HashService hashService;

    @Override
    public boolean supports(Class<?> paramClass) {
        return LoginDTO.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        LoginDTO loginDTO = (LoginDTO) obj;
        User user = userService.getUserByEmail(loginDTO.getEmail());
        if (user == null) {
            errors.rejectValue("password", "invalid", new Object[]{"'password'"},
                    "Incorrect username or password");
            return;
        }
        if (!userService.verifyUserCredentials(loginDTO.getEmail(), hashService.encryptPassword(loginDTO.getPassword(), user.getUserName()))) {
            errors.rejectValue("password", "invalid", new Object[]{"'password'"},
                    "Incorrect username or password");
        }
    }
}