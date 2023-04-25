package com.cxcacm.article.fillter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class CxcSecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("CXC_Authorization");
        String certification = request.getHeader("CXC_Certification");
        if (!Objects.equals(authorization, "CXCACMGoldMedal"))
            return;
        if (!Objects.equals(certification, "CXCInvincibleInTheWorld"))
            return;
        filterChain.doFilter(request, response);
    }
}