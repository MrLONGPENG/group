package com.lveqia.cloud.common.config;

import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class AuthFilter implements Filter {

    private Set<String> ALLOWED_PATHS;
    private PathMatcher matcher = new AntPathMatcher();
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    public AuthFilter(Set<String> set){
        this.ALLOWED_PATHS = set;
    }

    @Override
    public void init(FilterConfig filterConfig){
        logger.debug("AuthFilter->init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI();

        if(ALLOWED_PATHS.stream().anyMatch(pattern-> matcher.match(pattern,uri))
                || uri.startsWith("/feign") || uri.startsWith("/merge")){ // 放行URL
            logger.debug("AuthFilter->allowed {}", uri);
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        logger.debug("AuthFilter->doFilter {}", uri);
        UserInfo userInfo = AuthUtil.getUserInfo(httpServletRequest);
        if(userInfo == null) {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = httpServletResponse.getWriter();
            out.write(ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID));
            out.flush();
            out.close();
        } else {
            //利用原始的request对象创建自己扩展的request对象并添加自定义参数
            RequestParameterWrapper parameterWrapper = new RequestParameterWrapper(httpServletRequest);
            parameterWrapper.addUserInfo(userInfo);
            chain.doFilter(parameterWrapper, httpServletResponse);
        }
    }

    @Override
    public void destroy() {
        logger.debug("AuthFilter->destroy");
    }
}
