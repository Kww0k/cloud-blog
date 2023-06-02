package com.cxcacm.article.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    private Long id;
    private String nickname;
    private String url;
    private String createTime;
    private Long viewCount;
    private Long like;
    private Long comment;
    private Long collection;
    private Long articleCount;
}
