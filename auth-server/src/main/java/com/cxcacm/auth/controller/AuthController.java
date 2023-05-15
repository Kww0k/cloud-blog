package com.cxcacm.auth.controller;

import com.cxcacm.auth.entity.ResponseResult;
import com.cxcacm.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseResult token(Principal principal, @RequestParam Map<String, String> parameters) {
        return authService.token(principal, parameters);
    }

    @GetMapping("/logout")
    public ResponseResult logout() {
        return authService.logout();
    }
}
