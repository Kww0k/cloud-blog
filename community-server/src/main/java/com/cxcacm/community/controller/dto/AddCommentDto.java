package com.cxcacm.community.controller.dto;

import lombok.Data;

/**
 * @Author: Silvery
 * @Date: 2023/6/12 22:28
 */
@Data
public class AddCommentDto {
    private Long communityId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;

    private String commentTo;
}
