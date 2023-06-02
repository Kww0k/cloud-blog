package com.cxcacm.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.community.entity.Community;
import org.apache.ibatis.annotations.Mapper;


/**
 * 论坛表(Community)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-02 22:18:51
 */
@Mapper
public interface CommunityMapper extends BaseMapper<Community> {

}
