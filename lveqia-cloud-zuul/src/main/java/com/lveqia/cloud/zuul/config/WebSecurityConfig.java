package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.zuul.config.auth.*;
import com.lveqia.cloud.zuul.config.filter.AppTokenFilter;
import com.lveqia.cloud.zuul.config.auth.AuthEntryPoint;
import com.lveqia.cloud.zuul.config.auth.AppProvider;
import com.lveqia.cloud.zuul.config.filter.AuthTokenFilter;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * Created by sang on 2017/12/28.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppProvider appProvider;
    private final ConfigSource configSource;
    private final SysUserService sysUserService;
    private final AuthTokenFilter jwtTokenFilter;
    private final AuthLogoutHandler authLogoutHandler;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthFailureHandler authFailureHandler;
    private final UrlAccessDeniedHandler urlAccessDeniedHandler;
    private final AuthEntryPoint unauthorizedHandler;
    private final UrlAccessDecisionManager urlAccessDecisionManager;
    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);


    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public AppTokenFilter appTokenFilter() throws Exception {
        AppTokenFilter filer = new AppTokenFilter();
        filer.setAuthenticationManager(authenticationManagerBean());
        filer.setAuthenticationFailureHandler(authFailureHandler);
        filer.setAuthenticationSuccessHandler(authSuccessHandler);
        return filer;
    }

    @Autowired
    public WebSecurityConfig(AppProvider appProvider, ConfigSource configSource, SysUserService sysUserService
            , AuthTokenFilter jwtTokenFilter, AuthLogoutHandler authLogoutHandler
            , AuthSuccessHandler authSuccessHandler, AuthFailureHandler authFailureHandler
            , UrlAccessDeniedHandler urlAccessDeniedHandler, AuthEntryPoint unauthorizedHandler
            , UrlAccessDecisionManager urlAccessDecisionManager
            , UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource) {
        logger.debug("WebSecurityConfig");
        this.appProvider = appProvider;
        this.configSource = configSource;
        this.sysUserService = sysUserService;
        this.jwtTokenFilter = jwtTokenFilter;
        this.authLogoutHandler = authLogoutHandler;
        this.authSuccessHandler = authSuccessHandler;
        this.authFailureHandler = authFailureHandler;
        this.unauthorizedHandler = unauthorizedHandler;
        this.urlAccessDeniedHandler = urlAccessDeniedHandler;
        this.urlAccessDecisionManager = urlAccessDecisionManager;
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("AuthenticationManagerBuilder");
        auth.authenticationProvider(appProvider)
            .userDetailsService(sysUserService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/authority", "/error",  "/wx/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("WebSecurityConfig--http");
        http.csrf().disable() // 由于使用的是JWT，我们这里不需要csrf
        .exceptionHandling().accessDeniedHandler(urlAccessDeniedHandler).authenticationEntryPoint(unauthorizedHandler)
        // 基于token，所以不需要session
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // 放行所有 预检请求
        .and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
        // 设置跨域条件，以及增加授权设置
        .and().cors().configurationSource(configSource).and().authorizeRequests()
        // 自定义决策管理器(动态权限码)
        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        })
         // 设置Form登陆相关条件
        .and().formLogin().loginPage("/authority").loginProcessingUrl("/sys/login")
        .usernameParameter("username").passwordParameter("password").permitAll()
        // 增加登陆成功与失败结果出来
        .failureHandler(authFailureHandler).successHandler(authSuccessHandler)
        // 增加退出接口，以便注销Token以及其他
        .and().logout().logoutUrl("/sys/logout").addLogoutHandler(authLogoutHandler).permitAll();
        // 增加JWT (json web token) 过滤器
        http.addFilterBefore(appTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        http.headers().cacheControl();
    }
}