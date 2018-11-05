package com.lveqia.cloud.zuul.config.auth;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AppProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(AppProvider.class);

    private final SysUserService sysUserService;

    public AppProvider(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("getPrincipal {}", authentication.getPrincipal());
        UserDetails user = this.sysUserService.loadUserByUsername(authentication.getName());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        boolean hasAuthority = false;
        for (GrantedAuthority authority : authorities) {
            if (Constant.ROLE_OPERATE.equals(authority.getAuthority())
                    || Constant.ROLE_ADMIN.equals(authority.getAuthority())){
                hasAuthority = true;
                break;
            }
        }
        if(!hasAuthority) throw new AuthException(ResultUtil.CODE_UNAUTHORIZED);
        return new AppToken(user, authentication.getCredentials(), user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AppToken.class.isAssignableFrom(authentication);
    }
}
