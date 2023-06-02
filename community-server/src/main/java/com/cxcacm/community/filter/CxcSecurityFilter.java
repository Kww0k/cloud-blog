package com.cxcacm.community.filter;

import com.alibaba.fastjson.JSON;
import com.cxcacm.community.entity.ResponseResult;
import com.cxcacm.community.enums.AppHttpCodeEnum;
import com.cxcacm.community.utils.JwtUtil;
import com.cxcacm.community.utils.RedisCache;
import com.cxcacm.community.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.cxcacm.community.constants.CommunityConstants.*;


@Component
public class CxcSecurityFilter extends OncePerRequestFilter {

    private final RedisCache redisCache;

    @Autowired
    public CxcSecurityFilter(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AUTH_HEADER);
        String certification = request.getHeader(CERT_HEADER);
        if (!Objects.equals(authorization, AUTH_INFO) || !Objects.equals(certification, CERT_INFO)) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.CXC_AUTH);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        if (request.getRequestURL().toString().contains("/article/api/") || request.getRequestURL().toString().contains("/comment/api/")) {
            filterChain.doFilter(request, response);
            return;
        }
        String username;
        try {
            username = (String) JwtUtil.parseJWT(request.getHeader(AUTH_TOKEN).substring(TOKEN_START)).get(AUTH_USER);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.AUTH_EXPIRE);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        Object user = redisCache.getCacheObject(LOGIN_KEY + username);
        if (user == null) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        filterChain.doFilter(request, response);
    }
}