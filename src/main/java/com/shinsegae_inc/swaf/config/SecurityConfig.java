package com.shinsegae_inc.swaf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
* 
* @author  : 174309
* @since   : 2019. 1. 15. 오후 5:49:58
* @desc    : spring security filter 설정 (스프링시큐티리 필터 사용안함 처리)
* <pre>
* Comments : 
* </pre>
*/ 
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //http.authorizeRequests().antMatchers("/**").permitAll();
        http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();
    }
    
    @Override 
    public void configure(WebSecurity web) throws Exception { 
        web.ignoring().antMatchers("/**");
    }
    
}
