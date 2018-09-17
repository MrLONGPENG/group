package com.lveqia.cloud.zuul.config.auth;

import com.lveqia.cloud.zuul.config.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;


/**
 * Created by sang on 2017/12/28.
 * 当Url需要判断权限的时候，进入此处检查角色是否匹配
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, AuthenticationException {
        logger.debug("UrlAccessDecisionManager->decide");
        // 下面逻辑采用：当接口需要的权限只要用户存在其中一个即有权限
        for (ConfigAttribute ca : collection) {
            //当前请求需要的权限
            String needRole = ca.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                } else
                    return;
            }
            //当前用户所具有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                logger.debug("authority->{}", authority.getAuthority());
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        logger.debug("UrlAccessDecisionManager->supports");
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        logger.debug("UrlAccessDecisionManager->supports");
        return true;
    }
}