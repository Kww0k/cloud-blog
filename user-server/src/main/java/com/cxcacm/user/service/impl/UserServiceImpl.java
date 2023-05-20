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
    public ResponseResult setAvatar(UpdateAvatarDto updateAvatarDto) {
        if (updateAvatarDto.getUrl() == null)
            return ResponseResult.errorResult(URL_NULL);
        User user = BeanCopyUtils.copyBean(updateAvatarDto, User.class);
        baseMapper.updateById(user);
        Object cacheObject = redisCache.getCacheObject(URL_INFO + updateAvatarDto.getUsername());
        if (cacheObject != null) {
            Long id = ((Integer) ((JSONObject) cacheObject).get(USER_ID)).longValue();
            String nickname = (String) ((JSONObject) cacheObject).get(USER_NICKNAME);
            redisCache.setCacheObject(URL_INFO + updateAvatarDto.getUsername(), new UrlInfoVo(id, nickname, updateAvatarDto.getUrl()));
        }
        return ResponseResult.okResult(updateAvatarDto.getUrl());
    }

    @Override
    public ResponseResult changePassword(ChangePasswordDto changePasswordDto) {
        User user = baseMapper.selectById(changePasswordDto.getId());
        if (!bCryptPasswordEncoder().matches(user.getPassword(), changePasswordDto.getPassword()))
            return ResponseResult.errorResult(PASSWORD_ERROR);
        if (!Objects.equals(changePasswordDto.getNewPassword(), changePasswordDto.getConfirmPassword()))
            return ResponseResult.errorResult(PASSWORD_DIFFERENT);
        user.setPassword(bCryptPasswordEncoder().encode(changePasswordDto.getNewPassword()));
        baseMapper.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeInfo(ChangeInfoDto changeInfoDto) {
        User user = BeanCopyUtils.copyBean(changeInfoDto, User.class);
        baseMapper.updateById(user);
        Object cacheObject = redisCache.getCacheObject(URL_INFO + changeInfoDto.getUsername());
        if (cacheObject != null) {
            Long id = ((Integer) ((JSONObject) cacheObject).get(USER_ID)).longValue();
            String nickname = (String) ((JSONObject) cacheObject).get(USER_NICKNAME);
            if (changeInfoDto.getNickname() != null)
                nickname = changeInfoDto.getNickname();
            String url = (String) ((JSONObject) cacheObject).get(USER_URL);
            redisCache.setCacheObject(URL_INFO + changeInfoDto.getUsername(), new UrlInfoVo(id, nickname, url));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfoByName(String username) {
        Object cacheObject = redisCache.getCacheObject(URL_INFO + username);
        if (cacheObject != null) {
            Long id = ((Integer) ((JSONObject) cacheObject).get(USER_ID)).longValue();
            String nickname = (String) ((JSONObject) cacheObject).get(USER_NICKNAME);
            String url = (String) ((JSONObject) cacheObject).get(USER_URL);
            return ResponseResult.okResult(new UrlInfoVo(id, nickname ,url));
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = baseMapper.selectOne(wrapper);
        if (user == null)
            return ResponseResult.errorResult(USER_NULL);
        UrlInfoVo urlInfoVo = BeanCopyUtils.copyBean(user, UrlInfoVo.class);
        redisCache.setCacheObject(URL_INFO + username, urlInfoVo);
        return ResponseResult.okResult(urlInfoVo);
    }


}
