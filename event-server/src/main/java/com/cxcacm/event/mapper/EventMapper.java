package com.cxcacm.event.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.event.entity.Event;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Event)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-23 11:23:16
 */
@Mapper
public interface EventMapper extends BaseMapper<Event> {

}
