package com.cxcacm.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.community.controller.dto.AddCommunityDto;
import com.cxcacm.community.controller.dto.UpdateCommunityDto;
import com.cxcacm.community.entity.Community;
import com.cxcacm.community.entity.ResponseResult;


/**
 * 论坛表(Community)表服务接口
 *
 * @author makejava
 * @since 2023-06-02 22:18:51
 */
public interface CommunityService extends IService<Community> {

    ResponseResult addCommunity(AddCommunityDto addCommunityDto);

    ResponseResult updateViewCount(Long id);

    ResponseResult getCommunitiesList(String title, Integer pageNum, Integer pageSize);

    ResponseResult getTopList(Integer num);

    ResponseResult updateCommunity(UpdateCommunityDto updateCommunityDto);

    ResponseResult deleteById(Long id);

    ResponseResult getSelfCommunities(String status);

    ResponseResult getCommunityInfo(Long id);
}
