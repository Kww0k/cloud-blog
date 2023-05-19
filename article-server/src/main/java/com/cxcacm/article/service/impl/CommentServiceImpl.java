package com.cxcacm.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.controller.dto.AddCommentDto;
import com.cxcacm.article.entity.Comment;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.mapper.CommentMapper;
import com.cxcacm.article.service.CommentService;
import com.cxcacm.article.service.vo.CommentVo;
import com.cxcacm.article.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cxcacm.article.constants.ArticleConstants.ROOT_ID;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-05-19 14:16:38
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public ResponseResult commentList(Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, id);
        wrapper.orderByDesc(Comment::getCreateTime);
        wrapper.eq(Comment::getRootId, ROOT_ID);
        List<Comment> comments = baseMapper.selectList(wrapper);
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        for (CommentVo commentVo : commentVos) {
            LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Comment::getRootId, commentVo.getId());
            lambdaQueryWrapper.orderByAsc(Comment::getCreateTime);
            List<Comment> comments1 = baseMapper.selectList(lambdaQueryWrapper);
            List<CommentVo> children = BeanCopyUtils.copyBeanList(comments1, CommentVo.class);
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(commentVos);
    }

    @Override
    public ResponseResult comment(AddCommentDto addCommentDto) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        baseMapper.insert(comment);
        return ResponseResult.okResult();
    }
}
