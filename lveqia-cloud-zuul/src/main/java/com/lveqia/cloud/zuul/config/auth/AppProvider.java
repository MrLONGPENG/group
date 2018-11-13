package com.lveqia.cloud.zuul.config.auth;


import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class AppProvider implements AuthenticationProvider {

    private final SysUserService sysUserService;
    private final Logger logger = LoggerFactory.getLogger(AppProvider.class);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    public AppProvider(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

//    protected void additionalAuthenticationChecks(UserDetails userDetails
//            , UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        if (authentication.getCredentials() == null) {
//            logger.debug("Authentication failed: no credentials provided");
//            throw new BadCredentialsException(messages.getMessage(
//                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
//                    "Bad credentials"));
//        }
//        String presentedPassword = authentication.getCredentials().toString();
//        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
//            logger.debug("Authentication failed: password does not match stored value");
//            throw new BadCredentialsException(messages.getMessage(
//                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
//                    "Bad credentials"));
//        }
//        Collection<? extends GrantedAuthority> authorities =  userDetails.getAuthorities();
//        boolean hasAuthority = false;
//        for (GrantedAuthority authority : authorities) {
//            if (Constant.ROLE_OPERATE.equals(authority.getAuthority())
//                    || Constant.ROLE_ADMIN.equals(authority.getAuthority())){
//                hasAuthority = true;
//                break;
//            }
//        }
//        if(!hasAuthority) throw new AuthException(ResultUtil.CODE_UNAUTHORIZED);
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("getPrincipal {}", authentication.getPrincipal());
        UserDetails loadedUser = this.sysUserService.loadUserByUsername(authentication.getName());
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        check(loadedUser); // 检查账号情况
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(presentedPassword, loadedUser.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
        // 检查权限
        Collection<? extends GrantedAuthority> authorities = loadedUser.getAuthorities();
        boolean hasAuthority = false;
        for (GrantedAuthority authority : authorities) {
            if (Constant.ROLE_OPERATE.equals(authority.getAuthority())
                    || Constant.ROLE_ADMIN.equals(authority.getAuthority())){
                hasAuthority = true;
                break;
            }
        }
        if(!hasAuthority) throw new AuthException(ResultUtil.CODE_UNAUTHORIZED);
        return new AppToken(loadedUser, authentication.getCredentials(), loadedUser.getAuthorities());

    }

    private void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            logger.debug("User account is locked");

            throw new LockedException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.locked",
                    "User account is locked"));
        }

        if (!user.isEnabled()) {
            logger.debug("User account is disabled");

            throw new DisabledException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.disabled",
                    "User is disabled"));
        }

        if (!user.isAccountNonExpired()) {
            logger.debug("User account is expired");

            throw new AccountExpiredException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.expired",
                    "User account has expired"));
        }
    }




    @Override
    public boolean supports(Class<?> authentication) {
        return AppToken.class.isAssignableFrom(authentication);
    }
}
