package com.cxcacm.user.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfoVo {
    private Long id;
    private String nickname;
    private String url;
    private String createTime;
}
