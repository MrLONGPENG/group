package com.lveqia.cloud.zuul.config.filter;

import com.lveqia.cloud.zuul.config.auth.AppToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 运营工具APP登陆过滤器
 */
@Component
public class AppTokenFilter extends AbstractAuthenticationProcessingFilter {

    public AppTokenFilter() {
        super("/sys/third");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // TODO 此处可以效验更多的APP基本信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AppToken appToken = new AppToken(username, password);
        appToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return this.getAuthenticationManager().authenticate(appToken);
    }

}
