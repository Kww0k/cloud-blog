package com.cxcacm.article.controller;

import com.cxcacm.article.annotation.SystemLog;
import com.cxcacm.article.controller.dto.GiveLikeDto;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.service.ArticleCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    private final ArticleCollectionService articleCollectionService;

    @Autowired
    public CollectionController(ArticleCollectionService articleCollectionService) {
        this.articleCollectionService = articleCollectionService;
    }

    @PostMapping("/giveCollection")
    @SystemLog(businessName = "收藏")
    public ResponseResult giveLike(@RequestBody GiveLikeDto giveLikeDto) {
        return articleCollectionService.giveCollection(giveLikeDto);
    }

    @PostMapping("/deleteCollection")
    @SystemLog(businessName = "取消收藏")
    public ResponseResult deleteLike(@RequestBody GiveLikeDto giveLikeDto) {
        return articleCollectionService.deleteCollection(giveLikeDto);
    }

    @GetMapping("/getCollectionList")
    @SystemLog(businessName = "获取收藏列表")
    public ResponseResult getLikeList(@RequestParam String username) {
        return articleCollectionService.getCollectionList(username);
    }
}
