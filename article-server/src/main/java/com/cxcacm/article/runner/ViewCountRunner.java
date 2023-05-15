package com.cxcacm.article.runner;

import com.cxcacm.article.entity.Article;
import com.cxcacm.article.mapper.ArticleMapper;
import com.cxcacm.article.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cxcacm.article.constants.ArticleConstants.ARTICLE_VIEW_COUNT;

@Component
public class ViewCountRunner implements CommandLineRunner {

    private final ArticleMapper articleMapper;

    private final RedisCache redisCache;
    @Autowired
    public ViewCountRunner(ArticleMapper articleMapper, RedisCache redisCache) {
        this.articleMapper = articleMapper;
        this.redisCache = redisCache;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap(ARTICLE_VIEW_COUNT, viewCountMap);
    }
}
