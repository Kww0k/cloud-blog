package com.cxcacm.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.article.entity.Comment;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-19 14:16:36
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
