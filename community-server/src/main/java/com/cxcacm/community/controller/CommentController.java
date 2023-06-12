package com.cxcacm.community.controller;

import com.cxcacm.community.annotation.SystemLog;
import com.cxcacm.community.controller.dto.AddCommentDto;
import com.cxcacm.community.entity.ResponseResult;
import com.cxcacm.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communityComment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    @SystemLog(businessName = "添加评论")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto) {
        return commentService.addComment(addCommentDto);
    }

    @GetMapping("/api/getCommentList/{id}")
    @SystemLog(businessName = "获取评论列表")
    public ResponseResult getCommentList(@PathVariable Long id) {
        return commentService.getCommentList(id);
    }
}
