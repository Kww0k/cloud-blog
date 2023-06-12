package com.cxcacm.community.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Silvery
 * @Date: 2023/6/12 21:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityListVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //缩略图
    private String thumbnail;
}
