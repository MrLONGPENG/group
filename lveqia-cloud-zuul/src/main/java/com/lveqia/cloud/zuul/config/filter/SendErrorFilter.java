package com.lveqia.cloud.zuul.config.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SendErrorFilter extends ZuulFilter {
    private static final String GLOBAL_ERROR_FILTER = "global.error.filter";
    private static Logger logger = LoggerFactory.getLogger(SendErrorFilter.class);

    @Value("${error.path:/error}")
    private String errorPath;

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -1; //  SendErrorFilter 中 SEND_ERROR_FILTER_ORDER 为0
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // only forward to errorPath if it hasn't been forwarded to already
        return ctx.getThrowable() != null && !ctx.getBoolean(GLOBAL_ERROR_FILTER, false);
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            ZuulException exception = findZuulException(ctx.getThrowable());
            ctx.remove("throwable"); //保证系统的异常过滤器不再执行
            HttpServletRequest request = ctx.getRequest();
            logger.error("SendErrorFilter status:{} uri:{}", exception.nStatusCode, request.getRequestURI());
            request.setAttribute("javax.servlet.error.status_code", exception.nStatusCode);
            request.setAttribute("javax.servlet.error.exception", exception);
            if (StringUtils.hasText(exception.errorCause)) {
                request.setAttribute("javax.servlet.error.message", exception.errorCause);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPath);
            if (dispatcher != null) {
                ctx.set(GLOBAL_ERROR_FILTER, true);
                if (!ctx.getResponse().isCommitted()) {
                    ctx.setResponseStatusCode(exception.nStatusCode);
                    dispatcher.forward(request, ctx.getResponse());
                }
            }
        }
        catch (Exception ex) {
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;

    }

    private ZuulException findZuulException(Throwable throwable) {
        if (throwable.getCause() instanceof ZuulRuntimeException) {
            return (ZuulException) throwable.getCause().getCause();
        }
        if (throwable.getCause() instanceof ZuulException) {
            return (ZuulException) throwable.getCause();
        }
        if (throwable instanceof ZuulException) {
            return (ZuulException) throwable;
        }
        return new ZuulException(throwable, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
    }

}
