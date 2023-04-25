package com.cxcacm.user.service.impl;

import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.entity.User;
import com.cxcacm.user.enums.AppHttpCodeEnum;
import com.cxcacm.user.repo.UserRepository;
import com.cxcacm.user.service.UserService;
import com.cxcacm.user.service.vo.UserInfoVo;
import com.cxcacm.user.utils.BeanCopyUtils;
import com.cxcacm.user.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.cxcacm.user.constants.UserConstants.AUTH_TOKEN;
import static com.cxcacm.user.constants.UserConstants.AUTH_USER;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseResult getUserInfo() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader(AUTH_TOKEN);
        if (authorization == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        String jwt = authorization.substring(7);
        String username;
        try {
            username = (String) JwtUtil.parseJWT(jwt).get(AUTH_USER);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.AUTH_EXPIRE);
        }
        User user = userRepository.findByUsername(username);
        String loginCache = username + request.getRemoteAddr();
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
        String jwt = authorization.substring(7);
        String username;
        try {
            username = (String) JwtUtil.parseJWT(jwt).get(AUTH_USER);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.AUTH_EXPIRE);
        }
        String loginCache = username + request.getRemoteAddr();
        return ResponseResult.okResult();
    }
}
