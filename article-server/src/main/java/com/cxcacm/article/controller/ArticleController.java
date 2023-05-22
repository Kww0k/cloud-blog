package com.cxcacm.article.controller;

import com.cxcacm.article.annotation.SystemLog;
import com.cxcacm.article.controller.dto.AddArticleDto;
import com.cxcacm.article.controller.dto.UpdateArticleDto;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/addArticle")
    @SystemLog(businessName = "添加文章")
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @GetMapping("/api/getArticlePage")
    @SystemLog(businessName = "分页获取文章列表")
    public ResponseResult getArticleList() {
        return articleService.getArticleList();
    }

    @GetMapping("/api/getTopFourArticles")
    @SystemLog(businessName = "获取不同标签热度前4的文章信息")
    public ResponseResult getTopFourArticles(String tagName) {
        return articleService.getTopFourArticles(tagName);
    }

    @GetMapping("/api/getTopTenArticles")
    @SystemLog(businessName = "获取热度前10的文章信息")
    public ResponseResult getTopTenArticles() {
        return articleService.getTopTenArticles();
    }

    @GetMapping("/api/getArticleInfo/{id}")
    @SystemLog(businessName = "根据id获取文章的具体信息")
    public ResponseResult getArticleInfo(@PathVariable Long id) {
        return articleService.getArticleInfo(id);
    }

    @GetMapping("/getSelfArticles")
    @SystemLog(businessName = "获取个人的文章信息")
    public ResponseResult getSelfArticles(@RequestParam String username) {
        return articleService.getSelfArticles(username);
    }

    @GetMapping("/getSelfArticleDraft")
    @SystemLog(businessName = "获取个人的草稿文章信息")
    public ResponseResult getSelfArticleDraft(@RequestParam String username) {
        return articleService.getSelfArticleDraft(username);
    }

    @PostMapping("/updateArticle")
    @SystemLog(businessName = "更新文章信息")
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

    @DeleteMapping("/delete/{id}")
    @SystemLog(businessName = "删除文章")
    public ResponseResult deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }

    @GetMapping("/api/updateViewCount/{id}")
    @SystemLog(businessName = "更新浏览量")
    public ResponseResult updateViewCount(@PathVariable Long id) {
        return articleService.updateViewCount(id);
    }

    @GetMapping("/api/userInfo")
    @SystemLog(businessName = "获取个人信息")
    public ResponseResult userInfo(String username) {
        return articleService.userInfo(username);
    }
}
