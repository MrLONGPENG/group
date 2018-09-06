package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sang on 2017/12/28.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SysUserService sysUserService;
    private final UrlAccessDecisionManager urlAccessDecisionManager;
    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    public WebSecurityConfig(SysUserService sysUserService, UrlAccessDecisionManager urlAccessDecisionManager
            , UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource) {
        logger.debug("WebSecurityConfig");
        this.sysUserService = sysUserService;
        this.urlAccessDecisionManager = urlAccessDecisionManager;
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("WebSecurityConfig--auth");
        auth.userDetailsService(sysUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/authority", "/error",  "/wx/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("WebSecurityConfig--http");
        http.csrf().disable().cors().configurationSource(request -> {
            logger.debug("sessionId:{}", request.getSession().getId());
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("http://data.mujugroup.com");
            config.addAllowedOrigin("http://localhost:8080");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            return config;
        }).and().authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                /*.and().httpBasic()*/
                .and().formLogin().loginPage("/authority").loginProcessingUrl("/sys/login")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                        out.write(ResultUtil.error(ResultUtil.CODE_PASSWORD_ERROR));
                    } else if (e instanceof DisabledException) {
                        out.write(ResultUtil.error(ResultUtil.CODE_ACCOUNT_DISABLE));
                    } else {
                        out.write(ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR));
                    }
                    out.flush();
                    out.close();
                }).successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(ResultUtil.success(sysUserService.getCurrInfo()));
                    out.flush();
                    out.close();
                })
                .and().logout().logoutUrl("/sys/logout").invalidateHttpSession(true).permitAll()
                .and().exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
                    logger.debug("AuthenticationAccessDeniedHandler");
                    response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.write(ResultUtil.error(ResultUtil.CODE_NOT_AUTHORITY));
                    out.flush();
                    out.close();
                });
    }
}