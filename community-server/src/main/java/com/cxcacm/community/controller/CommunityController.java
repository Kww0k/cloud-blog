package com.cxcacm.community.controller;

import com.cxcacm.community.annotation.SystemLog;
import com.cxcacm.community.controller.dto.AddCommunityDto;
import com.cxcacm.community.controller.dto.UpdateCommunityDto;
import com.cxcacm.community.entity.ResponseResult;
import com.cxcacm.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping("/addCommunity")
    @SystemLog(businessName = "添加讨论")
    public ResponseResult addCommunity(@RequestBody AddCommunityDto addCommunityDto) {
        return communityService.addCommunity(addCommunityDto);
    }

    @GetMapping("/api/updateViewCount/{id}")
    @SystemLog(businessName = "更新浏览量")
    public ResponseResult updateViewCount(@PathVariable Long id) {
        return communityService.updateViewCount(id);
    }

    @GetMapping("/api/getCommunitiesList")
    @SystemLog(businessName = "分页获取讨论信息")
    public ResponseResult getCommunitiesList(String title, Integer pageNum, Integer pageSize) {
        return communityService.getCommunitiesList(title, pageNum, pageSize);
    }

    @GetMapping("/api/getCommunityInfo/{id}")
    @SystemLog(businessName = "分页获取讨论信息")
    public ResponseResult getCommunityInfo(@PathVariable Long id) {
        return communityService.getCommunityInfo(id);
    }

    @GetMapping("/api/getTopList")
    @SystemLog(businessName = "获取浏览量靠前的讨论")
    public ResponseResult getTopList(Integer num) {
        return communityService.getTopList(num);
    }

    @PostMapping("/updateCommunity")
    @SystemLog(businessName = "更新讨论的信息")
    public ResponseResult updateCommunity(@RequestBody UpdateCommunityDto updateCommunityDto) {
        return communityService.updateCommunity(updateCommunityDto);
    }

    @DeleteMapping("/deleteById/{id}")
    @SystemLog(businessName = "删除讨论")
    public ResponseResult deleteById(@PathVariable Long id) {
        return communityService.deleteById(id);
    }

    @GetMapping("/getSelfCommunities")
    @SystemLog(businessName = "获取个人发布的讨论")
    public ResponseResult getSelfCommunities(String status) {
        return communityService.getSelfCommunities(status);
    }

}
