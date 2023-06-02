package com.cxcacm.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.community.entity.CommunityCollection;
import com.cxcacm.community.mapper.CommunityCollectionMapper;
import com.cxcacm.community.service.CommunityCollectionService;
import org.springframework.stereotype.Service;

/**
 * 收藏表(CommunityCollection)表服务实现类
 *
 * @author makejava
 * @since 2023-06-02 22:18:31
 */
@Service("communityCollectionService")
public class CommunityCollectionServiceImpl extends ServiceImpl<CommunityCollectionMapper, CommunityCollection> implements CommunityCollectionService {

}
