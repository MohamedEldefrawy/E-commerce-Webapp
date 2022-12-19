//package com.vodafone.Ecommerce.model.dto;
//
//import com.vodafone.Ecommerce.model.Role;
//import lombok.*;
//
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//@ToString
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class CreateUser {
//
//    private String userName;
//    @NotNull
//    @NotBlank
//    private String email;
//    @NotNull
//    @NotBlank
//    @Size(min = 4, message = "Password can't be less than 8 characters")
//    @Size(max = 30, message = "Password exceeded 30 characters")
//    private String password;
//
//    private Role role;
//}
