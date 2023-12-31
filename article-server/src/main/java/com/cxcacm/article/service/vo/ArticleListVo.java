package com.cxcacm.article.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //缩略图
    private String thumbnail;
    private String createBy;
    private Long viewCount;
    private Long likeCount;
    private Long collectionCount;
    private Long commentCount;
    private List<String> tagList;
}
