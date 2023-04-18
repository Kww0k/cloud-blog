package com.cxcacm.auth.controller.api;

import com.cxcacm.auth.entity.User;
import com.cxcacm.auth.config.log.annotation.SystemLog;
import com.cxcacm.commons.entity.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    @SystemLog(businessName = "登录")
    public ResponseResult login(@RequestBody User user) {
        System.out.println(user);
        return ResponseResult.okResult();
    }
}
