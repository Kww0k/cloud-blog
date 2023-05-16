package com.cxcacm.article.job;

import com.cxcacm.article.entity.Article;
import com.cxcacm.article.service.ArticleService;
import com.cxcacm.article.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.cxcacm.article.constants.ArticleConstants.ARTICLE_VIEW_COUNT;

@Component
public class ViewCountJob {

    private final RedisCache redisCache;

    private final ArticleService articleService;

    @Autowired
    public ViewCountJob(RedisCache redisCache, ArticleService articleService) {
        this.redisCache = redisCache;
        this.articleService = articleService;
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount() {
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ARTICLE_VIEW_COUNT);
        List<Article> list = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .toList();
        articleService.updateBatchById(list);
    }
}
