package com.cxcacm.article.controller;

import com.cxcacm.article.controller.dto.AddCommentDto;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/commentList/{id}")
    public ResponseResult commentList(@PathVariable Long id) {
        return commentService.commentList(id);
    }

    @PostMapping("/addComment")
    public ResponseResult comment(@RequestBody AddCommentDto addCommentDto) {
        return commentService.comment(addCommentDto);
    }
}
