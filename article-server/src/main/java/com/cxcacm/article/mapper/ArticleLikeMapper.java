package com.cxcacm.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.article.entity.ArticleLike;
import org.apache.ibatis.annotations.Mapper;


/**
 * 点赞表(ArticleLike)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-15 17:42:17
 */
@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {

}
