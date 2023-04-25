package com.cxcacm.user.service.impl;

import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.entity.User;
import com.cxcacm.user.enums.AppHttpCodeEnum;
import com.cxcacm.user.repo.UserRepository;
import com.cxcacm.user.service.UserService;
import com.cxcacm.user.service.vo.UserInfoVo;
import com.cxcacm.user.utils.BeanCopyUtils;
import com.cxcacm.user.utils.JwtUtil;
import com.cxcacm.user.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.cxcacm.user.constants.UserConstants.*;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RedisCache redisCache;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RedisCache redisCache) {
        this.userRepository = userRepository;
        this.redisCache = redisCache;
    }

    @Override
    public ResponseResult getUserInfo() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader(AUTH_TOKEN);
        if (authorization == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        String jwt = authorization.substring(TOKEN_START);
        String username;
        try {
            username = (String) JwtUtil.parseJWT(jwt).get(AUTH_USER);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.AUTH_EXPIRE);
        }
        User user = userRepository.findByUsername(username);
        String loginCache = username + request.getRemoteAddr();
        redisCache.setCacheObject(LOGIN_KEY + loginCache, user);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult logout() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader(AUTH_TOKEN);
        if (authorization == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        String jwt = authorization.substring(TOKEN_START);
        String username;
        try {
            username = (String) JwtUtil.parseJWT(jwt).get(AUTH_USER);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.AUTH_EXPIRE);
        }
        String loginCache = username + request.getRemoteAddr();
        redisCache.deleteObject(LOGIN_KEY + loginCache);
        return ResponseResult.okResult();
    }
}
