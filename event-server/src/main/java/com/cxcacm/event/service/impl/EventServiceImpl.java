package com.cxcacm.event.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.event.entity.Event;
import com.cxcacm.event.entity.ResponseResult;
import com.cxcacm.event.mapper.EventMapper;
import com.cxcacm.event.service.EventService;
import org.springframework.stereotype.Service;

/**
 * (Event)表服务实现类
 *
 * @author makejava
 * @since 2023-05-23 11:23:18
 */
@Service("eventService")
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Override
    public ResponseResult getEventList() {
        return ResponseResult.okResult(list());
    }

    @Override
    public ResponseResult getEventById(Long id) {
        return ResponseResult.okResult(getById(id));
    }
}
