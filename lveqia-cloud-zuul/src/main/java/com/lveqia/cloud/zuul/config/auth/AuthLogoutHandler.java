package com.lveqia.cloud.zuul.config.auth;

import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthLogoutHandler implements LogoutHandler {
    private final StringRedisTemplate stringRedisTemplate;
    private final Logger logger = LoggerFactory.getLogger(AuthLogoutHandler.class);

    @Autowired
    public AuthLogoutHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) {
        logger.debug("AuthLogoutHandler uid:{} {}", AuthUtil.getUserInfo(request));
        stringRedisTemplate.delete(AuthUtil.getKey(AuthUtil.getUserInfo(request)));
        response.setContentType("application/json;charset=utf-8");
        try {
            PrintWriter  out = response.getWriter();
            out.write(ResultUtil.success("成功退出!"));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
