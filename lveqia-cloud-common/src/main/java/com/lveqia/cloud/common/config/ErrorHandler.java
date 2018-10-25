package com.lveqia.cloud.common.config;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * 全局异常捕获基类
 */
public abstract class ErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    /**
     * 未处理异常捕获
     */
    protected abstract String otherErrorHandler(Exception exception);
    /**
     * 全局错误异常捕获
     */
    @ExceptionHandler(value=Exception.class)
    public String globalErrorHandler(Exception exception){
        String other = otherErrorHandler(exception);
        if(other == null) logger.error("未处理异常 {}", exception);
        return other!= null ? other : ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR);
    }

    /**
     * 自定义错误异常捕获
     */
    @ExceptionHandler(value = BaseException.class)
    public String baseErrorHandler(BaseException exception) {
        return ResultUtil.error(exception.getCode(), exception.getMessage());
    }

    /**
     * 数据效验绑定异常
     */
    @ExceptionHandler(value = BindException.class)
    public String bindErrorHandler(BindException exception) {
        List<FieldError> fieldErrors= exception.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : fieldErrors){
            sb.append(error.getField()).append(Constant.SIGN_MAO_HAO);
            sb.append(error.getDefaultMessage()).append(Constant.SIGN_FEN_HAO);
        }
        logger.error(sb.toString());
        return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, sb.toString());
    }

    /**
     *  请求丢失必要参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public String missErrorHandler(MissingServletRequestParameterException exception) {
        return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, exception.getMessage());
    }



}
