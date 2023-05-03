package com.cxcacm.article.controller;

import com.cxcacm.article.annotation.SystemLog;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/getTagList")
    @SystemLog(businessName = "获取标签信息")
    public ResponseResult getTagList() {
        return tagService.getTagList();
    }
}
