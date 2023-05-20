package com.cxcacm.user.controller;

import com.cxcacm.user.annotation.SystemLog;
import com.cxcacm.user.controller.Dto.ChangeInfoDto;
import com.cxcacm.user.controller.Dto.ChangePasswordDto;
import com.cxcacm.user.controller.Dto.UpdateAvatarDto;
import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/setAvatar")
    @SystemLog(businessName = "设置用户头像")
    public ResponseResult serAvatar(@RequestBody UpdateAvatarDto updateAvatarDto) {
        return userService.setAvatar(updateAvatarDto);
    }

    @PostMapping("/changePassword")
    @SystemLog(businessName = "修改密码")
    public ResponseResult changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(changePasswordDto);
    }

    @PostMapping("/changeInfo")
    @SystemLog(businessName = "修改用户信息")
    public ResponseResult changeInfo(@RequestBody ChangeInfoDto changeInfoDto) {
        return userService.changeInfo(changeInfoDto);
    }

    @GetMapping("/getInfoByName")
    @SystemLog(businessName = "通过用户名获取信息")
    public ResponseResult getInfoByName(String username) {
        return userService.getInfoByName(username);
    }
}
