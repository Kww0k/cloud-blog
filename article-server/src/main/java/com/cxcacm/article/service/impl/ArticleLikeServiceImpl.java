package com.cxcacm.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.controller.dto.GiveLikeDto;
import com.cxcacm.article.entity.Article;
import com.cxcacm.article.entity.ArticleLike;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.mapper.ArticleLikeMapper;
import com.cxcacm.article.mapper.ArticleMapper;
import com.cxcacm.article.service.ArticleLikeService;
import com.cxcacm.article.service.vo.ArticleListVo;
import com.cxcacm.article.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.cxcacm.article.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * 点赞表(ArticleLike)表服务实现类
 *
 * @author makejava
 * @since 2023-05-15 17:42:17
 */
@Service("articleLikeService")
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleLikeServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public ResponseResult giveLike(GiveLikeDto giveLikeDto) {
        if (!StringUtils.hasText(giveLikeDto.getUsername()) || !StringUtils.hasText(String.valueOf(giveLikeDto.getId())))
            return ResponseResult.errorResult(SYSTEM_ERROR);
        save(new ArticleLike(giveLikeDto.getUsername(), giveLikeDto.getId()));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLike(GiveLikeDto giveLikeDto) {
        if (!StringUtils.hasText(giveLikeDto.getUsername()) || !StringUtils.hasText(String.valueOf(giveLikeDto.getId())))
            return ResponseResult.errorResult(SYSTEM_ERROR);
        LambdaQueryWrapper<ArticleLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleLike::getArticleId, giveLikeDto.getId());
        wrapper.eq(ArticleLike::getUsername, giveLikeDto.getUsername());
        baseMapper.delete(wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLikeList(String username) {
        LambdaQueryWrapper<ArticleLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleLike::getUsername, username);
        List<ArticleLike> articleLikes = baseMapper.selectList(wrapper);
        List<Long> idList = articleLikes.stream().map(ArticleLike::getArticleId).toList();
        List<Article> articles = articleMapper.selectBatchIds(idList);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(articleListVos);
    }
}
