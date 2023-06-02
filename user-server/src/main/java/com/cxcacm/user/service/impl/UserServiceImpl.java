package com.cxcacm.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.user.controller.Dto.ChangeInfoDto;
import com.cxcacm.user.controller.Dto.ChangePasswordDto;
import com.cxcacm.user.controller.Dto.UpdateAvatarDto;
import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.entity.User;
import com.cxcacm.user.mapper.UserMapper;
import com.cxcacm.user.service.UserService;
import com.cxcacm.user.service.vo.UrlInfoVo;
import com.cxcacm.user.utils.BeanCopyUtils;
import com.cxcacm.user.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.cxcacm.user.constants.UserConstants.*;
import static com.cxcacm.user.enums.AppHttpCodeEnum.*;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RedisCache redisCache;

    @Autowired
    public UserServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public ResponseResult setAvatar(UpdateAvatarDto updateAvatarDto) {
        if (!StringUtils.hasText(updateAvatarDto.getUrl()))
            return ResponseResult.errorResult(URL_NULL);
        User user = BeanCopyUtils.copyBean(updateAvatarDto, User.class);
        baseMapper.updateById(user);
        Object cacheObject = redisCache.getCacheObject(USER_INFO + updateAvatarDto.getUsername());
        if (cacheObject != null) {
            Long id = ((Integer) ((JSONObject) cacheObject).get(USER_ID)).longValue();
            String nickname = (String) ((JSONObject) cacheObject).get(USER_NICKNAME);
            String createTime = ((JSONObject) cacheObject).get(CREATE_TIME).toString();
            redisCache.setCacheObject(USER_INFO + updateAvatarDto.getUsername(), new UrlInfoVo(id, nickname, updateAvatarDto.getUrl(), createTime));
        } else
            ResponseResult.errorResult(NEED_LOGIN);
        return ResponseResult.okResult(updateAvatarDto.getUrl());
    }

    @Override
    @Transactional
    public ResponseResult changePassword(ChangePasswordDto changePasswordDto) {
        if (!StringUtils.hasText(changePasswordDto.getPassword()) ||
                !StringUtils.hasText(changePasswordDto.getNewPassword()) ||
                !StringUtils.hasText(changePasswordDto.getConfirmPassword()))
            return ResponseResult.errorResult(MISSING_PARAM);
        if (changePasswordDto.getNewPassword().length() > 18 ||
                changePasswordDto.getNewPassword().length() < 6)
            return ResponseResult.errorResult(PASSWORD_LENGTH);
        User user = baseMapper.selectById(changePasswordDto.getId());
        if (!bCryptPasswordEncoder().matches(changePasswordDto.getPassword(), user.getPassword()))
            return ResponseResult.errorResult(PASSWORD_ERROR);
        if (!Objects.equals(changePasswordDto.getNewPassword(), changePasswordDto.getConfirmPassword()))
            return ResponseResult.errorResult(PASSWORD_DIFFERENT);
        user.setPassword(bCryptPasswordEncoder().encode(changePasswordDto.getNewPassword()));
        baseMapper.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult changeInfo(ChangeInfoDto changeInfoDto) {
        if (changeInfoDto.getNickname().length() > 18 || changeInfoDto.getNickname().length() < 3)
            return ResponseResult.errorResult(USERNAME_LENGTH);
        User user = BeanCopyUtils.copyBean(changeInfoDto, User.class);
        baseMapper.updateById(user);
        Object cacheObject = redisCache.getCacheObject(USER_INFO + changeInfoDto.getUsername());
        if (cacheObject != null) {
            Long id = ((Integer) ((JSONObject) cacheObject).get(USER_ID)).longValue();
            String nickname = (String) ((JSONObject) cacheObject).get(USER_NICKNAME);
            if (StringUtils.hasText(changeInfoDto.getNickname()))
                nickname = changeInfoDto.getNickname();
            String url = (String) ((JSONObject) cacheObject).get(USER_URL);
            String createTime = ((JSONObject) cacheObject).get(CREATE_TIME).toString();
            redisCache.setCacheObject(USER_INFO + changeInfoDto.getUsername(), new UrlInfoVo(id, nickname, url, createTime));
        } else
            ResponseResult.errorResult(NEED_LOGIN);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfoByName(String username) {
        Object cacheObject = redisCache.getCacheObject(USER_INFO + username);
        if (cacheObject != null) {
            Long id = ((Integer) ((JSONObject) cacheObject).get(USER_ID)).longValue();
            String nickname = (String) ((JSONObject) cacheObject).get(USER_NICKNAME);
            String url = (String) ((JSONObject) cacheObject).get(USER_URL);
            String createTime = ((JSONObject) cacheObject).get(CREATE_TIME).toString();
            return ResponseResult.okResult(new UrlInfoVo(id, nickname, url, createTime));
        } else
            return ResponseResult.errorResult(NEED_LOGIN);
    }
}
