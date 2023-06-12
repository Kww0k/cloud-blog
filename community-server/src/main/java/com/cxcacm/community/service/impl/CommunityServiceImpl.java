package com.cxcacm.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.community.controller.dto.AddCommunityDto;
import com.cxcacm.community.controller.dto.UpdateCommunityDto;
import com.cxcacm.community.entity.Community;
import com.cxcacm.community.entity.ResponseResult;
import com.cxcacm.community.mapper.CommunityMapper;
import com.cxcacm.community.service.CommunityService;
import com.cxcacm.community.service.vo.CommunityInfoVo;
import com.cxcacm.community.service.vo.CommunityListVo;
import com.cxcacm.community.service.vo.CommunityPage;
import com.cxcacm.community.utils.BeanCopyUtils;
import com.cxcacm.community.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static com.cxcacm.community.constants.CommunityConstants.*;
import static com.cxcacm.community.enums.AppHttpCodeEnum.MISSING_PARAM;

/**
 * 论坛表(Community)表服务实现类
 *
 * @author makejava
 * @since 2023-06-02 22:18:51
 */
@Service("communityService")
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    private final RedisCache redisCache;

    @Autowired
    public CommunityServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 新增讨论
     *
     * @Author: Silvery
     * @Date: 2023/6/12 21:38
     * @param addCommunityDto
     * addCommunityDto为添加信息的实体类，标题，内容，和封面不能为空
     * @return ResponseResult
     */
    @Override
    public ResponseResult addCommunity(AddCommunityDto addCommunityDto) {
        if (!StringUtils.hasText(addCommunityDto.getTitle())
                || !StringUtils.hasText(addCommunityDto.getContent())
                || !StringUtils.hasText(addCommunityDto.getThumbnail()))
            return ResponseResult.errorResult(MISSING_PARAM); // 做非空判断，否则返回错误信息
        Community community = BeanCopyUtils.copyBean(addCommunityDto, Community.class);
        baseMapper.insert(community); // 插入信息
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(COMMUNITY_VIEW_COUNT);
        viewCountMap.put(community.getId().toString(), 0);
        redisCache.setCacheMap(COMMUNITY_VIEW_COUNT, viewCountMap); // 往redis的讨论流量量中添加一条信息
        return ResponseResult.okResult();
    }

    /**
     * 更新浏览量
     * @Author: Silvery
     * @Date: 2023/6/12 21:42
     * @param id
     * id为浏览的文章的id
     * @return ResponseResult
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(COMMUNITY_VIEW_COUNT, id.toString(), INCREMENT_VALUE); // 直接使用increment时他加1
        return ResponseResult.okResult();
    }

    /**
     * 分页查询数据
     *
     * @Author: Silvery
     * @Date: 2023/6/12 21:48
     * @param title 文章标题
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return ResponseResult
     */
    @Override
    public ResponseResult getCommunitiesList(String title, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Community> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(title))
            wrapper.like(Community::getTitle, title); // 判断是否需要根据讨论标题查询
        Long count = baseMapper.selectCount(wrapper); // 获取讨论数量
        Page<Community> page = page(new Page<>(pageNum, pageSize), wrapper);
        List<CommunityListVo> communityListVos = BeanCopyUtils.copyBeanList(page.getRecords(), CommunityListVo.class);
        return ResponseResult.okResult(new CommunityPage(count, communityListVos));
    }

    /**
     * 获取热门排行榜
     *
     * @Author: Silvery
     * @Date: 2023/6/12 22:00
     * @param num 获取的数量
     * @return ResponseResult
     */
    @Override
    public ResponseResult getTopList(Integer num) {
        LambdaQueryWrapper<Community> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Community::getViewCount); // 根据浏览量排序
        wrapper.eq(Community::getStatus, COMMUNITY_STATUE); // 需要是已经发布的讨论
        wrapper.last("limit " + num.toString()); // 根据传入的数量进行限制
        List<Community> communities = baseMapper.selectList(wrapper);
        List<CommunityListVo> communityListVos = BeanCopyUtils.copyBeanList(communities, CommunityListVo.class);
        return ResponseResult.okResult(communityListVos);
    }

    /**
     * 更新讨论的信息
     *
     * @Author: Silvery
     * @Date: 2023/6/12 22:08
     * @param updateCommunityDto 更新列表
     * @return ResponseResult
     */
    @Override
    public ResponseResult updateCommunity(UpdateCommunityDto updateCommunityDto) {
        Community community = BeanCopyUtils.copyBean(updateCommunityDto, Community.class);
        updateById(community);
        return ResponseResult.okResult();
    }

    /**
     * 根据id删除讨论
     *
     * @Author: Silvery
     * @Date: 2023/6/12 22:11
     * @param id 删除讨论的id
     * @return ResponseResult
     */
    @Override
    public ResponseResult deleteById(Long id) {
        baseMapper.deleteById(id);
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(COMMUNITY_VIEW_COUNT); // 同时也要删除缓存中的数据
        viewCountMap.remove(id.toString());
        redisCache.setCacheMap(COMMUNITY_VIEW_COUNT, viewCountMap);
        return ResponseResult.okResult();
    }

    /**
     * 获取个人发布的讨论
     *
     * @Author: Silvery
     * @Date: 2023/6/12 22:15
     * @param status 分类信息
     * @return ResponseResult
     */
    @Override
    public ResponseResult getSelfCommunities(String status) {
        LambdaQueryWrapper<Community> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Community::getStatus, status); // 根据发布状态分类
        List<Community> communities = baseMapper.selectList(wrapper);
        List<CommunityListVo> communityListVos = BeanCopyUtils.copyBeanList(communities, CommunityListVo.class);
        return ResponseResult.okResult(communityListVos);
    }


    /**
     * 获取讨论具体信息
     *
     * @Author: Silvery
     * @Date: 2023/6/12 22:37
     * @param id 讨论id
     * @return ResponseResult
     */
    @Override
    public ResponseResult getCommunityInfo(Long id) {
        Community community = getById(id);
        CommunityInfoVo communityInfoVo = BeanCopyUtils.copyBean(community, CommunityInfoVo.class); // 需要返回创建的用户，后门要用
        return ResponseResult.okResult(communityInfoVo);
    }


}
