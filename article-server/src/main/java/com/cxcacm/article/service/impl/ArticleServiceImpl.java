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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static com.cxcacm.article.constants.ArticleConstants.ARTICLE_STATUE;
import static com.cxcacm.article.enums.AppHttpCodeEnum.MISSING_PARAM;
import static com.cxcacm.article.enums.AppHttpCodeEnum.TOO_MANY_TAG;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-05-03 15:06:01
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleTagMapper articleTagMapper;

    @Autowired
    public ArticleServiceImpl(ArticleTagMapper articleTagMapper) {
        this.articleTagMapper = articleTagMapper;
    }

    @Override
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        if (addArticleDto.getContent() == null || addArticleDto.getSummary() == null ||
                addArticleDto.getTitle() == null || addArticleDto.getThumbnail() == null ||
                addArticleDto.getTagsList() == null || addArticleDto.getIsComment() == null)
            return ResponseResult.errorResult(MISSING_PARAM);
        if (addArticleDto.getTagsList().size() > 5)
            return ResponseResult.errorResult(TOO_MANY_TAG);
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
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
        List<TopTenArticleVo> topTenArticleVos = BeanCopyUtils.copyBeanList(articles, TopTenArticleVo.class);
        return ResponseResult.okResult(topTenArticleVos);
    }

    @Override
    public ResponseResult getSelfArticles(String username) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCreateBy, username);
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
}
