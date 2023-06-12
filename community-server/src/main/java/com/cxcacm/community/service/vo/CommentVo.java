package com.cxcacm.community.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    private Long id;
    //文章id
    private Long communityId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;

    private String nickname;

    private String url;

    private String commentTo;

    private Date createTime;

    private String createBy;

    List<CommentVo> children;
}
