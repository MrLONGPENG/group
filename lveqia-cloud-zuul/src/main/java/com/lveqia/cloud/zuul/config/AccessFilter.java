package com.lveqia.cloud.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class AccessFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        logger.debug("filterType");
        return "pre";
    }

    @Override
    public int filterOrder() {
        logger.debug("filterOrder");
        return 0; //优先级，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        logger.debug("shouldFilter");
        return true; //是否执行该过滤器，true代表需要过滤
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.debug("Method {} SessionId {} request url {}", request.getMethod()
                , request.getSession().getId(), request.getRequestURL().toString());
        //获取传来的参数accessToken
      /*  Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {
            logger.warn("access token is empty");
            //过滤该请求，不往下级服务去转发请求，到此结束
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"result\":\"accessToken is empty!\"}");
            return null;
        }*/
        return null;

    }
}
