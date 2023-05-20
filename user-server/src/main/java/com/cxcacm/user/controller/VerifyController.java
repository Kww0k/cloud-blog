package com.cxcacm.user.controller;

import com.cxcacm.user.annotation.SystemLog;
import com.cxcacm.user.controller.Dto.RegisterDto;
import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify")
public class VerifyController {

    private final VerifyService verifyService;

    @Autowired
    public VerifyController(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    @GetMapping("/api/getVerify")
    @SystemLog(businessName = "获取验证码")
    public ResponseResult getVerify(@RequestParam String email) {
        return verifyService.getVerify(email);
    }

    @PostMapping("/api/doRegister")
    @SystemLog(businessName = "注册用户")
    public ResponseResult doRegister(@RequestBody RegisterDto registerDto) {
        return verifyService.doRegister(registerDto);
    }
}
