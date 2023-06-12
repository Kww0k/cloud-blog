package com.cxcacm.community.controller;

import com.cxcacm.community.annotation.SystemLog;
import com.cxcacm.community.controller.dto.GiveLikeDto;
import com.cxcacm.community.entity.ResponseResult;
import com.cxcacm.community.service.CommunityCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communityCollection")
public class CommunityCollectionController {

    private final CommunityCollectionService communityCollectionService;

    @Autowired
    public CommunityCollectionController(CommunityCollectionService communityCollectionService) {
        this.communityCollectionService = communityCollectionService;
    }

    @PostMapping("/giveCollection")
    @SystemLog(businessName = "收藏")
    public ResponseResult giveLike(@RequestBody GiveLikeDto giveLikeDto) {
        return communityCollectionService.giveCollection(giveLikeDto);
    }

    @PostMapping("/deleteCollection")
    @SystemLog(businessName = "取消收藏")
    public ResponseResult deleteLike(@RequestBody GiveLikeDto giveLikeDto) {
        return communityCollectionService.deleteCollection(giveLikeDto);
    }

    @GetMapping("/getCollectionList")
    @SystemLog(businessName = "获取收藏列表")
    public ResponseResult getLikeList(@RequestParam String username) {
        return communityCollectionService.getCollectionList(username);
    }
}
