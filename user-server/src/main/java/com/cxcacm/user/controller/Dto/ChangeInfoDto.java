package com.cxcacm.user.controller.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeInfoDto {
    private Long id;
    private String username;
    //昵称
    private String nickname;
    //性别
    private String sex;
    //邮箱
    private String email;
}
