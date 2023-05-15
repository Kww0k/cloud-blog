package com.cxcacm.auth.service.impl;

import com.cxcacm.auth.entity.ResponseResult;
import com.cxcacm.auth.entity.User;
import com.cxcacm.auth.repo.UserRepository;
import com.cxcacm.auth.service.AuthService;
import com.cxcacm.auth.service.vo.TokenVo;
import com.cxcacm.auth.service.vo.UserInfoVo;
import com.cxcacm.auth.utils.BeanCopyUtils;
import com.cxcacm.auth.utils.JwtUtil;
import com.cxcacm.auth.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

import static com.cxcacm.auth.constants.AuthConstants.*;
import static com.cxcacm.auth.enums.AppHttpCodeEnum.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final TokenEndpoint tokenEndpoint;
    private final TokenStore tokenStore;
    private final UserRepository userRepository;
    private final RedisCache redisCache;

    @Autowired
    public AuthServiceImpl(TokenEndpoint tokenEndpoint, TokenStore tokenStore, UserRepository userRepository, RedisCache redisCache) {
        this.tokenEndpoint = tokenEndpoint;
        this.tokenStore = tokenStore;
        this.userRepository = userRepository;
        this.redisCache = redisCache;
    }

    @Override
    public ResponseResult token(Principal principal, Map<String, String> parameters) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        //调用自带的获取token方法。
        OAuth2AccessToken resultToken;
        try {
            resultToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        } catch (HttpRequestMethodNotSupportedException e) {
            throw new RuntimeException(e);
        }
        if (resultToken != null) {
            String username = (String) JwtUtil.parseJWT(resultToken.getValue()).get(AUTH_USER);
            User user = userRepository.findByUsername(username);
            UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
            TokenVo tokenVo = new TokenVo(resultToken.getValue(), resultToken.getRefreshToken().getValue(), "bearer ", resultToken.getExpiresIn(), userInfoVo);
            redisCache.setCacheObject(LOGIN_KEY + username, user);
            return ResponseResult.okResult(tokenVo);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @Override
    public ResponseResult logout() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader(AUTH_TOKEN);
        if (authorization == null)
            return ResponseResult.errorResult(NEED_LOGIN);
        String token = authorization.substring(TOKEN_START);
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        if (oAuth2AccessToken != null) {
            // 移除access_token
            tokenStore.removeAccessToken(oAuth2AccessToken);
            // 移除refresh_token
            if (oAuth2AccessToken.getRefreshToken() != null) {
                tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
            }
        }
        String username;
        try {
            username = (String) JwtUtil.parseJWT(token).get(AUTH_USER);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AUTH_EXPIRE);
        }
        redisCache.deleteObject(LOGIN_KEY + username);
        return ResponseResult.okResult();
    }
}
