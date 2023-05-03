package com.cxcacm.article.config;

import com.cxcacm.article.fillter.CxcSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                .addFilterBefore(cxcSecurityFilter, BearerTokenAuthenticationFilter.class);
    }
}
