package com.cxcacm.article.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageVo {
    List<ArticleListVo> articleListVos;
    Long count;
}
