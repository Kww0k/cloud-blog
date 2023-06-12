package com.cxcacm.community.runner;

import com.cxcacm.community.utils.RedisCache;
import com.cxcacm.community.entity.Community;
import com.cxcacm.community.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cxcacm.community.constants.CommunityConstants.COMMUNITY_VIEW_COUNT;

@Component
public class ViewCountRunner implements CommandLineRunner {

    private final CommunityMapper communityMapper;

    private final RedisCache redisCache;
    @Autowired
    public ViewCountRunner(CommunityMapper communityMapper, RedisCache redisCache) {
        this.communityMapper = communityMapper;
        this.redisCache = redisCache;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Community> communities = communityMapper.selectList(null);
        Map<String, Integer> viewCountMap = communities.stream()
                .collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap(COMMUNITY_VIEW_COUNT, viewCountMap);
    }
}
