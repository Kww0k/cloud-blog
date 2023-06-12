package com.cxcacm.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.community.entity.Comment;
import com.cxcacm.community.entity.ResponseResult;
import com.cxcacm.community.controller.dto.AddCommentDto;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-06-02 22:18:51
 */
public interface CommentService extends IService<Comment> {

    ResponseResult addComment(AddCommentDto addCommentDto);

    ResponseResult getCommentList(Long id);
}
