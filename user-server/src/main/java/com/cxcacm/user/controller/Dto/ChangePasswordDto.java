package com.cxcacm.user.controller.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    private Long id;
    private String password;
    private String newPassword;
    private String confirmPassword;
}
