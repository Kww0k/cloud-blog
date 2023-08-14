package com.cxcacm.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.community.controller.dto.GiveLikeDto;
import com.cxcacm.community.entity.Community;
import com.cxcacm.community.entity.CommunityCollection;
import com.cxcacm.community.entity.ResponseResult;
import com.cxcacm.community.mapper.CommunityCollectionMapper;
import com.cxcacm.community.mapper.CommunityMapper;
import com.cxcacm.community.service.CommunityCollectionService;
import com.cxcacm.community.service.vo.CommunityListVo;
import com.cxcacm.community.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.cxcacm.community.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * 收藏表(CommunityCollection)表服务实现类
 *
 * @author makejava
 * @since 2023-06-02 22:18:31
 */
@Service("communityCollectionService")
@RequiredArgsConstructor
public class CommunityCollectionServiceImpl extends ServiceImpl<CommunityCollectionMapper, CommunityCollection> implements CommunityCollectionService {

    private final CommunityMapper communityMapper;

    /**
     * 收藏
     *
     * @Author: Silvery
     * @Date: 2023/6/12 22:22
     * @param giveLikeDto 收藏信息
     * @return ResponseResult
     */
    @Override
    public ResponseResult giveCollection(GiveLikeDto giveLikeDto) {
        if (!StringUtils.hasText(giveLikeDto.getUsername()) || !StringUtils.hasText(String.valueOf(giveLikeDto.getId())))
            return ResponseResult.errorResult(SYSTEM_ERROR);
        save(new CommunityCollection(giveLikeDto.getUsername(), giveLikeDto.getId()));
        return ResponseResult.okResult();
    }

    /**
     * 取消收藏
     *
     * @Author: Silvery
     * @Date: 2023/6/12 22:22
     * @param giveLikeDto 取消收藏的信息
     * @return ResponseResult
     */
    @Override
    public ResponseResult deleteCollection(GiveLikeDto giveLikeDto) {
        if (!StringUtils.hasText(giveLikeDto.getUsername()) || !StringUtils.hasText(String.valueOf(giveLikeDto.getId())))
            return ResponseResult.errorResult(SYSTEM_ERROR);
        LambdaQueryWrapper<CommunityCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommunityCollection::getArticleId, giveLikeDto.getId());
        wrapper.eq(CommunityCollection::getUsername, giveLikeDto.getUsername());
        baseMapper.delete(wrapper);
        return ResponseResult.okResult();
    }

    /**
     * 获取个人收藏列表
     *
     * @Author: Silvery
     * @Date: 2023/6/12 22:22
     * @param username 用户名
     * @return ResponseResult
     */
    @Override
    public ResponseResult getCollectionList(String username) {
        LambdaQueryWrapper<CommunityCollection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommunityCollection::getUsername, username);
        List<CommunityCollection> articleCollections = baseMapper.selectList(wrapper);
        List<Long> idList = articleCollections.stream().map(CommunityCollection::getArticleId).toList();
        List<Community> communities = communityMapper.selectBatchIds(idList);
        List<CommunityListVo> communityListVos = BeanCopyUtils.copyBeanList(communities, CommunityListVo.class);
        return ResponseResult.okResult(communityListVos);
    }
}
