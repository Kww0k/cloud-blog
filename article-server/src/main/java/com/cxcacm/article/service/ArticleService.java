package com.cxcacm.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.article.controller.dto.AddArticleDto;
import com.cxcacm.article.controller.dto.UpdateArticleDto;
import com.cxcacm.article.entity.Article;
import com.cxcacm.article.entity.ResponseResult;


/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-05-03 15:06:01
 */
public interface ArticleService extends IService<Article> {

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult getArticleList();

    ResponseResult getTopFourArticles(String tagName);

    ResponseResult getArticleInfo(Long id);

    ResponseResult getTopTenArticles();

    ResponseResult getSelfArticles(String username);

    ResponseResult updateArticle(UpdateArticleDto article);

    ResponseResult deleteArticle(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult getSelfArticleDraft(String username);

    ResponseResult userInfo(String username);
}
