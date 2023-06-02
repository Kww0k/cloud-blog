package com.cxcacm.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.controller.dto.AddCommentDto;
import com.cxcacm.article.entity.Comment;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.mapper.CommentMapper;
import com.cxcacm.article.service.CommentService;
import com.cxcacm.article.service.vo.CommentVo;
import com.cxcacm.article.utils.BeanCopyUtils;
import com.cxcacm.article.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.cxcacm.article.constants.ArticleConstants.*;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-05-19 14:16:38
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final RedisCache redisCache;

    @Autowired
    public CommentServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public ResponseResult commentList(Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, id);
        wrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> comments = baseMapper.selectList(wrapper);
        for (Comment comment : comments) {
            Object commentUserInfo = redisCache.getCacheObject(USER_INFO + comment.getCreateBy());
            String nickname = (String) ((JSONObject) commentUserInfo).get(USER_NICKNAME);
            String url = (String) ((JSONObject) commentUserInfo).get(USER_URL);
            comment.setNickname(nickname);
            comment.setUrl(url);
            if (comment.getCommentTo() != null) {
                Object commentToUserInfo = redisCache.getCacheObject(USER_INFO + comment.getCommentTo());
                String toNickname = (String) ((JSONObject) commentToUserInfo).get(USER_NICKNAME);
                comment.setCommentTo(toNickname);
            }
        }
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        List<CommentVo> parentNodes = commentVos.stream()
                .filter(commentVo -> commentVo.getRootId() == -1L)
                .toList();
        for (CommentVo parentNode : parentNodes) {
            parentNode.setChildren(commentVos.stream()
                    .filter(commentVo -> Objects.equals(commentVo.getRootId(), parentNode.getId()))
                    .sorted(Comparator.comparing(CommentVo::getCreateTime))
                    .toList());
        }
        return ResponseResult.okResult(parentNodes);
    }

    @Override
    public ResponseResult comment(AddCommentDto addCommentDto) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        baseMapper.insert(comment);
        return ResponseResult.okResult();
    }
}
