package com.cxcacm.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.article.entity.Article;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-03 15:05:59
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
