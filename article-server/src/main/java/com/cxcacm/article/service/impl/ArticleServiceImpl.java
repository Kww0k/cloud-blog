package com.cxcacm.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.controller.dto.AddArticleDto;
import com.cxcacm.article.entity.Article;
import com.cxcacm.article.entity.ArticleTag;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.mapper.ArticleMapper;
import com.cxcacm.article.mapper.ArticleTagMapper;
import com.cxcacm.article.service.ArticleService;
import com.cxcacm.article.service.vo.*;
import com.cxcacm.article.utils.BeanCopyUtils;
import com.cxcacm.article.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.cxcacm.article.constants.ArticleConstants.*;
import static com.cxcacm.article.enums.AppHttpCodeEnum.*;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-05-03 15:06:01
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleTagMapper articleTagMapper;
    private final RedisCache redisCache;

    @Autowired
    public ArticleServiceImpl(ArticleTagMapper articleTagMapper, RedisCache redisCache) {
        this.articleTagMapper = articleTagMapper;
        this.redisCache = redisCache;
    }

    @Override
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        if (addArticleDto.getContent() == null || addArticleDto.getSummary() == null ||
                addArticleDto.getTitle() == null || addArticleDto.getThumbnail() == null ||
                addArticleDto.getTagsList() == null || addArticleDto.getIsComment() == null)
            return ResponseResult.errorResult(MISSING_PARAM);
        if (addArticleDto.getTagsList().size() > 5)
            return ResponseResult.errorResult(TOO_MANY_TAG);
        if (addArticleDto.getContent().contains("国") ||
                addArticleDto.getContent().contains("习近平") ||
                addArticleDto.getContent().contains("中国") ||
                addArticleDto.getContent().contains("陈轩丞") ||
                addArticleDto.getTitle().contains("国") ||
                addArticleDto.getTitle().contains("习近平") ||
                addArticleDto.getTitle().contains("中国") ||
                addArticleDto.getTitle().contains("陈轩丞"))
            return ResponseResult.errorResult(SENSITIVE_WORDS);
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ARTICLE_VIEW_COUNT);
        viewCountMap.put(article.getId().toString(), 0);
        redisCache.setCacheMap(ARTICLE_VIEW_COUNT, viewCountMap);
        for (String tag : addArticleDto.getTagsList())
            articleTagMapper.insert(new ArticleTag(article.getId(), tag));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getId);
        wrapper.eq(Article::getStatus, ARTICLE_STATUE);
        Long count = baseMapper.selectCount(null);
        Page<Article> page = page(new Page<>(pageNum, pageSize), wrapper);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        for (ArticleListVo article: articleListVos) {
            Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEW_COUNT, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        ArticlePageVo articlePageVo = new ArticlePageVo(articleListVos, count);
        return ResponseResult.okResult(articlePageVo);
    }

    @Override
    public ResponseResult getTopFourArticles(String tagName) {
        List<Article> articles = baseMapper.getTopFourArticles(tagName);
        List<TopFourArticleVo> topFourArticleVos = BeanCopyUtils.copyBeanList(articles, TopFourArticleVo.class);
        return ResponseResult.okResult(topFourArticleVos);
    }

    @Override
    public ResponseResult getArticleInfo(Long id) {
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> articleTags = articleTagMapper.selectList(wrapper);
        Article article = baseMapper.selectById(id);
        Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEW_COUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        ArticleInfoVo articleInfoVo = BeanCopyUtils.copyBean(article, ArticleInfoVo.class);
        articleTags.stream()
                .map(articleTag -> articleInfoVo.getTagName().add(articleTag.getTag()));
        return ResponseResult.okResult(articleInfoVo);
    }

    @Override
    public ResponseResult getTopTenArticles() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getViewCount);
        wrapper.eq(Article::getStatus, ARTICLE_STATUE);
        wrapper.last("limit 10");
        List<Article> articles = baseMapper.selectList(wrapper);
        for (Article article: articles) {
            Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEW_COUNT, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        List<TopTenArticleVo> topTenArticleVos = BeanCopyUtils.copyBeanList(articles, TopTenArticleVo.class);
        return ResponseResult.okResult(topTenArticleVos);
    }

    @Override
    public ResponseResult getSelfArticles(String username) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCreateBy, username);
        wrapper.eq(Article::getStatus, ARTICLE_STATUE);
        wrapper.orderByDesc(Article::getIsTop).orderByDesc(Article::getViewCount).orderByDesc(Article::getCreateTime);
        List<Article> articles = baseMapper.selectList(wrapper);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(articleListVos);
    }

    @Override
    public ResponseResult updateArticle(Article article) {
        save(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(ARTICLE_VIEW_COUNT, id.toString(), INCREMENT_VALUE);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getSelfArticleDraft(String username) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCreateBy, username);
        wrapper.eq(Article::getStatus, ARTICLE_STATUE_DRAFT);
        List<Article> articles = baseMapper.selectList(wrapper);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(articleListVos);
    }
}
