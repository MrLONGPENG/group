package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.ResultUtil;

public class ParamException extends Exception {

    public int getCode() {
        return ResultUtil.CODE_REQUEST_FORMAT;
    }

    //有参的构造方法
    public ParamException(String message){
        super(message);
    }

}
