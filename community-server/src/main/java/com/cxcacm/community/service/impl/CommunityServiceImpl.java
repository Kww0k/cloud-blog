package com.cxcacm.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.community.entity.Community;
import com.cxcacm.community.mapper.CommunityMapper;
import com.cxcacm.community.service.CommunityService;
import org.springframework.stereotype.Service;

/**
 * 论坛表(Community)表服务实现类
 *
 * @author makejava
 * @since 2023-06-02 22:18:51
 */
@Service("communityService")
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {
}
