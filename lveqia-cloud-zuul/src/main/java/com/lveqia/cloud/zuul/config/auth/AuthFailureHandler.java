package com.lveqia.cloud.zuul.config.auth;

import com.lveqia.cloud.common.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(AuthFailureHandler.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException{
        logger.debug("AuthFailureHandler");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            out.write(ResultUtil.error(ResultUtil.CODE_PASSWORD_ERROR));
        } else if (exception instanceof DisabledException) {
            out.write(ResultUtil.error(ResultUtil.CODE_ACCOUNT_DISABLE));
        } else {
            out.write(ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR));
        }
        out.flush();
        out.close();
    }
}
