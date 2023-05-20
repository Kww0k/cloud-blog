package com.cxcacm.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.user.controller.Dto.ChangeInfoDto;
import com.cxcacm.user.controller.Dto.ChangePasswordDto;
import com.cxcacm.user.controller.Dto.UpdateAvatarDto;
import com.cxcacm.user.entity.ResponseResult;
import com.cxcacm.user.entity.User;

public interface UserService extends IService<User> {

    ResponseResult setAvatar(UpdateAvatarDto updateAvatarDto);

    ResponseResult changePassword(ChangePasswordDto changePasswordDto);

    ResponseResult changeInfo(ChangeInfoDto changeInfoDto);

    ResponseResult getInfoByName(String username);
}
