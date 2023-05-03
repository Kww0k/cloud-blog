package com.cxcacm.user.controller;

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

    @GetMapping("/getVerify")
    public ResponseResult getVerify(@RequestParam String email) {
        return verifyService.getVerify(email);
    }

    @PostMapping("/doRegister")
    public ResponseResult doRegister(@RequestBody RegisterDto registerDto) {
        return verifyService.doRegister(registerDto);
    }
}
