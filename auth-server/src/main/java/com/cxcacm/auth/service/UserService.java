package com.cxcacm.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.auth.controller.dto.LoginDto;
import com.cxcacm.commons.entity.User;
import com.cxcacm.commons.entity.ResponseResult;

public interface UserService extends IService<User> {

    ResponseResult login(LoginDto user);

    ResponseResult logout();
}