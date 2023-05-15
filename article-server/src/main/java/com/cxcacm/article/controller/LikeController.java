package com.cxcacm.article.controller;

import com.cxcacm.article.annotation.SystemLog;
import com.cxcacm.article.controller.dto.GiveLikeDto;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.service.ArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final ArticleLikeService articleLikeService;

    @Autowired
    public LikeController(ArticleLikeService articleLikeService) {
        this.articleLikeService = articleLikeService;
    }

    @PostMapping("/giveLike")
    @SystemLog(businessName = "点赞")
    public ResponseResult giveLike(@RequestBody GiveLikeDto giveLikeDto) {
        return articleLikeService.giveLike(giveLikeDto);
    }

    @PostMapping("/deleteLike")
    @SystemLog(businessName = "取消点赞")
    public ResponseResult deleteLike(@RequestBody GiveLikeDto giveLikeDto) {
        return articleLikeService.deleteLike(giveLikeDto);
    }

    @GetMapping("/getLikeList")
    @SystemLog(businessName = "获取点赞列表")
    public ResponseResult getLikeList(@RequestParam String username) {
        return articleLikeService.getLikeList(username);
    }
}
