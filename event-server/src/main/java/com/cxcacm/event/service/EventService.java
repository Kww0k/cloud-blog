package com.cxcacm.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.event.entity.Event;
import com.cxcacm.event.entity.ResponseResult;


/**
 * (Event)表服务接口
 *
 * @author makejava
 * @since 2023-05-23 11:23:17
 */
public interface EventService extends IService<Event> {

    ResponseResult getEventList();

    ResponseResult getEventById(Long id);
}
