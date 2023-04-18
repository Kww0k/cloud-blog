package com.cxcacm.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.auth.controller.dto.LoginDto;
import com.cxcacm.commons.entity.LoginUser;
import com.cxcacm.commons.entity.User;
import com.cxcacm.auth.filter.security.SecurityUtils;
import com.cxcacm.auth.mapper.UserMapper;
import com.cxcacm.auth.service.UserService;
import com.cxcacm.auth.service.vo.AdminLoginVO;
import com.cxcacm.auth.service.vo.UserInfoVo;
import com.cxcacm.commons.entity.ResponseResult;
import com.cxcacm.commons.utils.BeanCopyUtils;
import com.cxcacm.commons.utils.JwtUtil;
import com.cxcacm.auth.filter.security.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.cxcacm.auth.constants.AuthConstants.LOGIN_INFO;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private final AuthenticationManager authenticationManager;
    private final RedisCache redisCache;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, RedisCache redisCache) {
        this.authenticationManager = authenticationManager;
        this.redisCache = redisCache;
    }

    @Override
    public ResponseResult login(LoginDto user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate))
            return ResponseResult.errorResult(400, "用户名或密码错误");
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        redisCache.setCacheObject(LOGIN_INFO + id, loginUser);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminLoginVO adminLoginVO = new AdminLoginVO(jwt, userInfoVo);
        return ResponseResult.okResult(adminLoginVO);
    }

    @Override
    public ResponseResult logout() {
        Long id = SecurityUtils.getUserId();
        redisCache.deleteObject(LOGIN_INFO + id);
        return ResponseResult.okResult();
    }
}