package com.cxcacm.user.controller;

import com.cxcacm.user.annotation.SystemLog;
import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    public ResponseResult getUserInfo() {
        return userService.getUserInfo();
    }



    @GetMapping("/logout")
    @SystemLog(businessName = "退出登录")
    public ResponseResult logout() {
        return userService.logout();
    }
}
