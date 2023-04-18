package com.cxcacm.auth.filter.security;

import com.alibaba.fastjson.JSON;
import com.cxcacm.commons.entity.ResponseResult;
import com.cxcacm.commons.enums.AppHttpCodeEnum;
import com.cxcacm.commons.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        authException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
