package com.cxcacm.file.config;

import com.cxcacm.file.fillter.CxcSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CxcSecurityFilter cxcSecurityFilter;

    @Autowired
    public SecurityConfiguration(CxcSecurityFilter cxcSecurityFilter) {
        this.cxcSecurityFilter = cxcSecurityFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/file/download/**").permitAll() // 对 文件下载接口进行放行
                .anyRequest().authenticated();
        http
                .addFilterBefore(cxcSecurityFilter, BearerTokenAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/file/download/**"); // 对 文件下载接口进行放行
    }
}
