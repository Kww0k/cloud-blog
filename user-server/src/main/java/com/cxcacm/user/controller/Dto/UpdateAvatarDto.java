package com.cxcacm.user.controller.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAvatarDto {
    private Long id;
    private String username;
    private String url;
}
