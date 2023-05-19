package com.cxcacm.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.article.controller.dto.AddCommentDto;
import com.cxcacm.article.entity.Comment;
import com.cxcacm.article.entity.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-05-19 14:16:38
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long id);

    ResponseResult comment(AddCommentDto addCommentDto);
}
