package com.mujugroup.wx.config;

import com.lveqia.cloud.common.config.ErrorHandler;
import com.lveqia.cloud.common.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class WxErrorHandler extends ErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(WxErrorHandler.class);

    @Override
    protected String otherErrorHandler(Exception exception) {
        logger.error("CoreErrorHandler find error {}", exception);
        return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR);
    }

}
