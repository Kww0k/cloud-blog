package com.cxcacm.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.community.entity.CommunityCollection;
import org.apache.ibatis.annotations.Mapper;


/**
 * 收藏表(CommunityCollection)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-02 22:18:29
 */
@Mapper
public interface CommunityCollectionMapper extends BaseMapper<CommunityCollection> {

}
