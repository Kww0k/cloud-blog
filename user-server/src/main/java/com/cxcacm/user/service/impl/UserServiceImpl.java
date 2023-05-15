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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static com.cxcacm.user.constants.UserConstants.*;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseResult setAvatar(String username, String url) {
        User byUsername = userRepository.findByUsername(username);
        byUsername.setUrl(url);
        userRepository.save(byUsername);
        return ResponseResult.okResult(url);
    }



}
