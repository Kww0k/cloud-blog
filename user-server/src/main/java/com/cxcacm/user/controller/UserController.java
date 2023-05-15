package com.cxcacm.user.controller;

import com.cxcacm.user.annotation.SystemLog;
import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/setAvatar")
    @SystemLog(businessName = "设置用户头像")
    public ResponseResult serAvatar(@RequestParam String username, @RequestParam String url) {
        return userService.setAvatar(username, url);
    }

}
