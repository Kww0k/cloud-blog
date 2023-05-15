package com.cxcacm.user.service;


import com.cxcacm.user.entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    ResponseResult setAvatar(String username, String url);
}
