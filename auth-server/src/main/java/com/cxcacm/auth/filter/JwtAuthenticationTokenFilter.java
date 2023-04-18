package com.cxcacm.auth.filter;

import com.alibaba.fastjson.JSON;
import com.cxcacm.commons.entity.LoginUser;
import com.cxcacm.commons.entity.ResponseResult;
import com.cxcacm.commons.enums.AppHttpCodeEnum;
import com.cxcacm.commons.utils.JwtUtil;
import com.cxcacm.auth.filter.security.RedisCache;
import com.cxcacm.commons.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.cxcacm.auth.constants.AuthConstants.AUTH_TOKEN;
import static com.cxcacm.auth.constants.AuthConstants.LOGIN_INFO;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final RedisCache redisCache;

    @Autowired
    public JwtAuthenticationTokenFilter(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTH_TOKEN);
        if (!StringUtils.hasText(token)) {
            // 说明该接口不需要登陆 直接放行
            filterChain.doFilter(request, response);
            return;
        }
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        String userId = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject(LOGIN_INFO + userId);
        if (Objects.isNull(loginUser)) {
            ResponseResult responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(responseResult));
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
