package com.cxcacm.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.controller.dto.AddArticleDto;
import com.cxcacm.article.controller.dto.UpdateArticleDto;
import com.cxcacm.article.entity.*;
import com.cxcacm.article.enums.AppHttpCodeEnum;
import com.cxcacm.article.mapper.*;
import com.cxcacm.article.service.ArticleService;
import com.cxcacm.article.service.vo.*;
import com.cxcacm.article.utils.BeanCopyUtils;
import com.cxcacm.article.utils.JwtUtil;
import com.cxcacm.article.utils.RedisCache;
import com.cxcacm.article.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private final ArticleLikeMapper articleLikeMapper;
    private final ArticleCollectionMapper articleCollectionMapper;
    private final CommentMapper commentMapper;
    private final RedisCache redisCache;

    @Autowired
    public ArticleServiceImpl(ArticleTagMapper articleTagMapper, ArticleLikeMapper articleLikeMapper, ArticleCollectionMapper articleCollectionMapper, CommentMapper commentMapper, RedisCache redisCache) {
        this.articleTagMapper = articleTagMapper;
        this.articleLikeMapper = articleLikeMapper;
        this.articleCollectionMapper = articleCollectionMapper;
        this.commentMapper = commentMapper;
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
        baseMapper.insert(article);
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
        Long count = baseMapper.selectCount(wrapper);
        Page<Article> page = page(new Page<>(pageNum, pageSize), wrapper);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        for (ArticleListVo article : articleListVos) {
            Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEW_COUNT, article.getId().toString());
            article.setViewCount(viewCount.longValue());
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId, article.getId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            List<String> tagList = new ArrayList<>();
            for (ArticleTag articleTag : articleTags)
                tagList.add(articleTag.getTag());
            LambdaQueryWrapper<ArticleLike> articleLikeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleLikeLambdaQueryWrapper.eq(ArticleLike::getArticleId, article.getId());
            Long likeCount = articleLikeMapper.selectCount(articleLikeLambdaQueryWrapper);
            article.setLikeCount(likeCount);
            LambdaQueryWrapper<ArticleCollection> articleCollectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleCollectionLambdaQueryWrapper.eq(ArticleCollection::getArticleId, article.getId());
            Long collectionCount = articleCollectionMapper.selectCount(articleCollectionLambdaQueryWrapper);
            article.setCollectionCount(collectionCount);
            article.setTagList(tagList);
            LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            commentLambdaQueryWrapper.eq(Comment::getArticleId, article.getId());
            Long commentCount = commentMapper.selectCount(commentLambdaQueryWrapper);
            article.setCommentCount(commentCount);
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
        List<String> tagList = new ArrayList<>();
        for (ArticleTag articleTag : articleTags)
            tagList.add(articleTag.getTag());
        Article article = baseMapper.selectById(id);
        Integer viewCount = redisCache.getCacheMapValue(ARTICLE_VIEW_COUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        ArticleInfoVo articleInfoVo = BeanCopyUtils.copyBean(article, ArticleInfoVo.class);
        LambdaQueryWrapper<ArticleLike> articleLikeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLikeLambdaQueryWrapper.eq(ArticleLike::getArticleId, articleInfoVo.getId());
        Long likeCount = articleLikeMapper.selectCount(articleLikeLambdaQueryWrapper);
        articleInfoVo.setLikeCount(likeCount);
        LambdaQueryWrapper<ArticleCollection> articleCollectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleCollectionLambdaQueryWrapper.eq(ArticleCollection::getArticleId, articleInfoVo.getId());
        Long collectionCount = articleCollectionMapper.selectCount(articleCollectionLambdaQueryWrapper);
        articleInfoVo.setCollectionCount(collectionCount);
        articleInfoVo.setTagList(tagList);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getHeader(AUTH_TOKEN) != null) {
                String username;
                try {
                    username = (String) JwtUtil.parseJWT(request.getHeader(AUTH_TOKEN).substring(TOKEN_START)).get(AUTH_USER);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseResult.errorResult(AppHttpCodeEnum.AUTH_EXPIRE);
                }
                articleCollectionLambdaQueryWrapper.eq(ArticleCollection::getUsername, username);
                Long isCollection = articleCollectionMapper.selectCount(articleCollectionLambdaQueryWrapper);
                articleInfoVo.setCollection(isCollection > 0);
                articleLikeLambdaQueryWrapper.eq(ArticleLike::getUsername, username);
                Long isLike = articleLikeMapper.selectCount(articleLikeLambdaQueryWrapper);
                articleInfoVo.setLike(isLike > 0);
            } else {
                articleInfoVo.setLike(false);
                articleInfoVo.setCollection(false);
            }
        } else {
            articleInfoVo.setLike(false);
            articleInfoVo.setCollection(false);
        }
        return ResponseResult.okResult(articleInfoVo);
    }

    @Override
    public ResponseResult getTopTenArticles() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getViewCount);
        wrapper.eq(Article::getStatus, ARTICLE_STATUE);
        wrapper.last("limit 10");
        List<Article> articles = baseMapper.selectList(wrapper);
        for (Article article : articles) {
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
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        if (updateArticleDto.getId() == null) return ResponseResult.errorResult(MISSING_PARAM);
        if (updateArticleDto.getTagsList() != null && updateArticleDto.getTagsList().size() > 5)
            return ResponseResult.errorResult(TOO_MANY_TAG);
        if (updateArticleDto.getTitle() != null && updateArticleDto.getContent() != null) {
            if (Objects.requireNonNull(updateArticleDto.getContent()).contains("习近平") ||
                    updateArticleDto.getContent().contains("国") ||
                    updateArticleDto.getContent().contains("中国") ||
                    updateArticleDto.getContent().contains("陈轩丞") ||
                    Objects.requireNonNull(updateArticleDto.getTitle()).contains("国") ||
                    updateArticleDto.getTitle().contains("习近平") ||
                    updateArticleDto.getTitle().contains("中国") ||
                    updateArticleDto.getTitle().contains("陈轩丞"))
                return ResponseResult.errorResult(SENSITIVE_WORDS);
        }
        if (updateArticleDto.getTagsList() != null) {
            LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTag::getArticleId, updateArticleDto.getId());
            articleTagMapper.delete(wrapper);
            for (String tag : updateArticleDto.getTagsList())
                articleTagMapper.insert(new ArticleTag(updateArticleDto.getId(), tag));
        }
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        baseMapper.deleteById(id);
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ARTICLE_VIEW_COUNT);
        viewCountMap.remove(id.toString());
        redisCache.setCacheMap(ARTICLE_VIEW_COUNT, viewCountMap);
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
