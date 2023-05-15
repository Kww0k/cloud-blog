package com.cxcacm.auth.service;

import com.cxcacm.auth.entity.ResponseResult;

import java.security.Principal;
import java.util.Map;

public interface AuthService {
    ResponseResult token(Principal principal, Map<String, String> parameters);

    ResponseResult logout();
}
