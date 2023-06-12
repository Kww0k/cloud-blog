package com.cxcacm.article.config;

import com.cxcacm.article.filter.CxcSecurityFilter;
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
                .anyRequest().authenticated();
        http
                .addFilterBefore(cxcSecurityFilter, BearerTokenAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/article/api/**" , "/comment/api/**");
    }
}
