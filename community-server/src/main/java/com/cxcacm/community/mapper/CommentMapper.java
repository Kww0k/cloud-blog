package com.cxcacm.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-02 22:18:51
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
