package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.zuul.config.auth.AuthSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class ConfigSource implements CorsConfigurationSource {
    private final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);

    @Override
    public CorsConfiguration getCorsConfiguration(@Nullable HttpServletRequest request) {
        Optional.ofNullable(request).ifPresent(req ->logger.debug("ConfigSource sessionId:{}",req.getSession().getId()));
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://data.mujugroup.com");
        config.addAllowedOrigin("http://test.mujugroup.com");
        config.addAllowedOrigin("http://dev.mujugroup.com");
        config.addAllowedOrigin("http://leo.mujugroup.com");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        return config;
    }
}
