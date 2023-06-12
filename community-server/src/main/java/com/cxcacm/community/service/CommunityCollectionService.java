package com.cxcacm.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.community.controller.dto.GiveLikeDto;
import com.cxcacm.community.entity.CommunityCollection;
import com.cxcacm.community.entity.ResponseResult;


/**
 * 收藏表(CommunityCollection)表服务接口
 *
 * @author makejava
 * @since 2023-06-02 22:18:30
 */
public interface CommunityCollectionService extends IService<CommunityCollection> {

    ResponseResult giveCollection(GiveLikeDto giveLikeDto);

    ResponseResult deleteCollection(GiveLikeDto giveLikeDto);

    ResponseResult getCollectionList(String username);
}
