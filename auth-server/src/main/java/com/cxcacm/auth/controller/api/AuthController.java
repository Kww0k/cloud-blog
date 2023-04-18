package com.cxcacm.auth.controller.api;

import com.cxcacm.auth.controller.dto.LoginDto;
import com.cxcacm.auth.service.UserService;
import com.cxcacm.commons.annotation.SystemLog;
import com.cxcacm.commons.entity.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @SystemLog(businessName = "登录")
    public ResponseResult login(@RequestBody LoginDto user) {
        return userService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "退出登陆")
    public ResponseResult logout() {
        return userService.logout();
    }
}
