package com.cxcacm.user.fillter;

import com.cxcacm.user.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.cxcacm.user.constants.UserConstants.*;

@Component
public class CxcSecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AUTH_HEADER);
        String certification = request.getHeader(CERT_HEADER);
        if (!Objects.equals(authorization, AUTH_INFO))
            return;
        if (!Objects.equals(certification, CERT_INFO))
            return;
        String username = (String) JwtUtil.parseJWT(request.getHeader(AUTH_TOKEN).substring(7)).get(AUTH_USER);
        String loginCache = username + request.getRemoteAddr();
        filterChain.doFilter(request, response);
    }
}