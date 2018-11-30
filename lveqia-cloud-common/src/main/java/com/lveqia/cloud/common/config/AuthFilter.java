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
import java.util.HashSet;
import java.util.Set;

public class AuthFilter implements Filter {

    public final static boolean MODE_REFUSE = true;    // 拒绝模式
    private final static boolean MODE_PERMIT = false;   // 许可模式

    private boolean currMode;
    private Set<String> allPaths;
    private PathMatcher matcher = new AntPathMatcher();
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    public AuthFilter(){
        this(MODE_PERMIT);
    }

    public AuthFilter(Set<String> set){
        this(set, MODE_PERMIT);
    }

    public AuthFilter(boolean currMode){
        this(new HashSet<>(), currMode);
    }

    public AuthFilter(Set<String> allPaths, boolean currMode){
        this.allPaths = allPaths;
        this.currMode = currMode;
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
        if(((HttpServletResponse) response).getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            logger.error("AuthFilter->has 500 error {}", uri);
            returnResult(httpServletResponse, ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR));
            return;
        }
        if((!currMode && allPaths.stream().anyMatch(pattern-> matcher.match(pattern,uri)))  // 许可模式
                || (currMode && allPaths.stream().noneMatch(pattern-> matcher.match(pattern, uri))) // 拒绝模式
                || uri.startsWith("/feign") || uri.startsWith("/merge")){   // 内部调用放行
            logger.debug("AuthFilter->allowed {}", uri);
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        UserInfo userInfo = AuthUtil.getUserInfo(httpServletRequest);
        if(userInfo == null) {
            logger.debug("AuthFilter->user info is null, url: {}", uri);
            returnResult(httpServletResponse, ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID));
        } else {
            //利用原始的request对象创建自己扩展的request对象并添加自定义参数
            logger.debug("AuthFilter->add uid to request, url: {}", uri);
            RequestParameterWrapper parameterWrapper = new RequestParameterWrapper(httpServletRequest);
            parameterWrapper.addUserInfo(userInfo);
            chain.doFilter(parameterWrapper, httpServletResponse);
        }
    }

    /**
     * 返回结果
     * @param response 返回对象
     * @param massage 返回信息
     */
    private void returnResult(HttpServletResponse response, String massage) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.write(massage);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        logger.debug("AuthFilter->destroy");
    }
}
