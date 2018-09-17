package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class TokenException extends Exception {


    public int getCode() {
        return ResultUtil.CODE_VALIDATION_FAIL;
    }
    //有参的构造方法
    public TokenException(){
        super("Token异常");
    }

}
