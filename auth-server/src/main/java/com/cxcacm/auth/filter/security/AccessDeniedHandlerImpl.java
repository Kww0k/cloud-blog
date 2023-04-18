package com.cxcacm.auth.filter.security;

import com.alibaba.fastjson.JSON;
import com.cxcacm.commons.entity.ResponseResult;
import com.cxcacm.commons.enums.AppHttpCodeEnum;
import com.cxcacm.commons.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        accessDeniedException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
