package com.cxcacm.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.article.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-04 00:26:33
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}
