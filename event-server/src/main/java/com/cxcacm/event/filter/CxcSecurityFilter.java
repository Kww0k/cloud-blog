package com.cxcacm.event.filter;

import com.alibaba.fastjson.JSON;
import com.cxcacm.event.entity.ResponseResult;
import com.cxcacm.event.enums.AppHttpCodeEnum;
import com.cxcacm.event.utils.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.cxcacm.event.constants.EventConstants.*;

@Component
public class CxcSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AUTH_HEADER);
        String certification = request.getHeader(CERT_HEADER);
        if (!Objects.equals(authorization, AUTH_INFO) || !Objects.equals(certification, CERT_INFO)) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.CXC_AUTH);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        filterChain.doFilter(request, response);
    }
}