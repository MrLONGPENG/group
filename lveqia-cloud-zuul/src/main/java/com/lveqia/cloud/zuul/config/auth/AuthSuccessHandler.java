package com.lveqia.cloud.zuul.config.auth;

import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        logger.debug("getPrincipal {}", authentication.getPrincipal());
        PrintWriter out = response.getWriter();
        SysUser user = (SysUser) authentication.getPrincipal();
        UserInfo userInfo = new UserInfo(user.getId(), user.getName(), user.getUsername());
        userInfo.setToken(AuthUtil.generateToken(userInfo));
        out.write(ResultUtil.success(userInfo));
        out.flush();
        out.close();
    }
}
