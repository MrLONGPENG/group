package com.lveqia.cloud.zuul.config.jwt;

import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final SysUserService sysUserService;
    private static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);


    @Autowired
    public JwtTokenFilter(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            String authToken =  AuthUtil.getToken(request);
            String username = AuthUtil.getUsernameFromToken(authToken);
            logger.info("checking authentication " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // TODO 此处可以改为不查数据库
                SysUser user = (SysUser) this.sysUserService.loadUserByUsername(username);
                UserInfo userInfo = new UserInfo(user.getId(), user.getName(), user.getUsername());
                if (AuthUtil.validateToken(authToken, userInfo)){
                    logger.info("authenticated user " + username + ", setting security context");
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);

        }

    }
}
