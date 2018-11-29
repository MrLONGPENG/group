package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class TokenException extends BaseException {
    public static final String WX_CODE_VALIDATION_FAIL = "Code效验失败";

    //有参的构造方法
    public TokenException(){
        super(ResultUtil.CODE_VALIDATION_FAIL, "Token异常");
    }

    public TokenException(String message){
        super(ResultUtil.CODE_VALIDATION_FAIL, message);
    }

}
