package com.cxcacm.auth.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.cxcacm.auth.constants.AuthConstants.*;

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
        filterChain.doFilter(request, response);
    }
}
