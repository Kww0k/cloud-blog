package com.cxcacm.community.controller.dto;

import lombok.Data;

/**
 * @Author: Silvery
 * @Date: 2023/6/12 22:07
 */
@Data
public class UpdateCommunityDto {
    //标题
    private String title;
    //文章内容
    private String content;
    //缩略图
    private String thumbnail;
    //状态（0已发布，1草稿）
    private String status;
    private String isComment;
}
