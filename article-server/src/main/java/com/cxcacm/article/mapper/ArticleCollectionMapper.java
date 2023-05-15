package com.cxcacm.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.article.entity.ArticleCollection;
import org.apache.ibatis.annotations.Mapper;


/**
 * 收藏表(ArticleCollection)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-15 17:42:08
 */
@Mapper
public interface ArticleCollectionMapper extends BaseMapper<ArticleCollection> {

}
