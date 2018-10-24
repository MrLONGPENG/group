package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.common.config.ErrorHandler;
import com.lveqia.cloud.common.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ZuulErrorHandler extends ErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(ZuulErrorHandler.class);

    @Override
    protected String otherErrorHandler(Exception exception) {
        logger.error("ZuulErrorHandler find error {}", exception);
        return ResultUtil.error(ResultUtil.CODE_VALIDATION_FAIL);
    }
}
