package com.cxcacm.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.controller.dto.AddArticleDto;
import com.cxcacm.article.entity.Article;
import com.cxcacm.article.entity.ArticleTag;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.mapper.ArticleMapper;
import com.cxcacm.article.mapper.ArticleTagMapper;
import com.cxcacm.article.service.ArticleService;
import com.cxcacm.article.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        for (Long tagId : addArticleDto.getTagsList())
            articleTagMapper.insert(new ArticleTag(article.getId(), tagId));
        return ResponseResult.okResult();
    }
}
