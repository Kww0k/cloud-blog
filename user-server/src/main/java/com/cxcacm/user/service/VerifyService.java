package com.cxcacm.user.service;

import com.cxcacm.user.controller.Dto.RegisterDto;
import com.cxcacm.user.entity.ResponseResult;

public interface VerifyService {
    ResponseResult getVerify(String email);

    ResponseResult doRegister(RegisterDto registerDto);
}
