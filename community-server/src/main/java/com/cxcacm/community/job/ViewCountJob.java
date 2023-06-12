package com.cxcacm.community.job;

import com.cxcacm.community.entity.Community;
import com.cxcacm.community.mapper.CommunityMapper;
import com.cxcacm.community.service.CommunityService;
import com.cxcacm.community.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.cxcacm.community.constants.CommunityConstants.COMMUNITY_VIEW_COUNT;


@Component
public class ViewCountJob {

    private final RedisCache redisCache;

    private final CommunityService communityService;

    @Autowired
    public ViewCountJob(RedisCache redisCache, CommunityService communityService) {
        this.redisCache = redisCache;
        this.communityService = communityService;
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount() {
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(COMMUNITY_VIEW_COUNT);
        List<Community> list = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Community(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .toList();
        communityService.updateBatchById(list);
    }
}
