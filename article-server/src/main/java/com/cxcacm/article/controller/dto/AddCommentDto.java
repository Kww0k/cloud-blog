package com.cxcacm.article.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentDto {
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
}
