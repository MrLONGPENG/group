package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class TokenException extends BaseException {
    //有参的构造方法
    public TokenException(){
        super(ResultUtil.CODE_VALIDATION_FAIL, "Token异常");
    }

}
