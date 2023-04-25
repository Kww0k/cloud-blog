package com.cxcacm.user.service;


import com.cxcacm.user.entity.ResponseResult;

public interface UserService {
    ResponseResult getUserInfo();

    ResponseResult logout();
}
