package com.cxcacm.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.community.controller.dto.AddCommentDto;
import com.cxcacm.community.entity.Comment;
import com.cxcacm.community.entity.ResponseResult;
import com.cxcacm.community.mapper.CommentMapper;
import com.cxcacm.community.service.CommentService;
import com.cxcacm.community.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-06-02 22:18:51
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public ResponseResult addComment(AddCommentDto addCommentDto) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        baseMapper.insert(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCommentList(Long id) {

        return null;
    }
}
