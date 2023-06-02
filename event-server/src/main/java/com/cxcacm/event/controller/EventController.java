package com.cxcacm.event.controller;

import com.cxcacm.event.annotation.SystemLog;
import com.cxcacm.event.entity.ResponseResult;
import com.cxcacm.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/getEventList")
    @SystemLog(businessName = "获取配置环境列表")
    public ResponseResult getEventList() {
        return eventService.getEventList();
    }

    @GetMapping("/getEventById/{id}")
    @SystemLog(businessName = "根据id获取配置环境文件")
    public ResponseResult getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }
}
