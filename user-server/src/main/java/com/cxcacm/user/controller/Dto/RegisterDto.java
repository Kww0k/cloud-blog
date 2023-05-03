package com.cxcacm.user.controller.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String username;

    private String password;

    private String confirmPassword;

    private String email;
    private String code;
}
