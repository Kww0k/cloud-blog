package com.cxcacm.article.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopFourArticleVo {
    private Long id;
    //标题
    private String title;
    private String createBy;
    //文章摘要
    private String summary;
    //缩略图
    private String thumbnail;

}
