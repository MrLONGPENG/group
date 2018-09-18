package com.lveqia.cloud.zuul.config.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("JwtProvider {}", authentication);
        // TODO 当前无需自己实现，DaoAuthenticationProvider已经完全可以达到条件
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
