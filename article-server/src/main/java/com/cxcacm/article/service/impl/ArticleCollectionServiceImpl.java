package com.cxcacm.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.controller.dto.GiveLikeDto;
import com.cxcacm.article.entity.Article;
import com.cxcacm.article.entity.ArticleCollection;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.mapper.ArticleCollectionMapper;
import com.cxcacm.article.mapper.ArticleMapper;
import com.cxcacm.article.service.ArticleCollectionService;
import com.cxcacm.article.service.vo.ArticleListVo;
import com.cxcacm.article.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cxcacm.article.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * 收藏表(ArticleCollection)表服务实现类
 *
 * @author makejava
 * @since 2023-05-15 17:42:10
 */
@Service("articleCollectionService")
public class ArticleCollectionServiceImpl extends ServiceImpl<ArticleCollectionMapper, ArticleCollection> implements ArticleCollectionService {

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleCollectionServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }


    @Override
    public ResponseResult giveCollection(GiveLikeDto giveLikeDto) {
        if (giveLikeDto.getUsername() == null || giveLikeDto.getId() == null)
            return ResponseResult.errorResult(SYSTEM_ERROR);
        save(new ArticleCollection(giveLikeDto.getUsername(), giveLikeDto.getId()));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCollection(GiveLikeDto giveLikeDto) {
        if (giveLikeDto.getUsername() == null || giveLikeDto.getId() == null)
            return ResponseResult.errorResult(SYSTEM_ERROR);
        LambdaQueryWrapper<ArticleCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleCollection::getArticleId, giveLikeDto.getId());
        wrapper.eq(ArticleCollection::getUsername, giveLikeDto.getUsername());
        baseMapper.delete(wrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCollectionList(String username) {
        LambdaQueryWrapper<ArticleCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleCollection::getUsername, username);
        List<ArticleCollection> articleCollections = baseMapper.selectList(wrapper);
        List<Long> idList = articleCollections.stream().map(ArticleCollection::getArticleId).toList();
        List<Article> articles = articleMapper.selectBatchIds(idList);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(articleListVos);
    }
}
