package com.cxcacm.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.entity.ArticleTag;
import com.cxcacm.article.mapper.ArticleTagMapper;
import com.cxcacm.article.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-05-04 00:26:33
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
