package com.cxcacm.article.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //缩略图
    private String thumbnail;
    private String isTop;
    private String status;
    //是否允许评论 1是，0否
    private String isComment;
    private String createBy;
    private Date createTime;
    private List<String> tagList;
    private Long viewCount;
    private Long likeCount;
    private Long collectionCount;
    private boolean like;
    private boolean collection;
}
