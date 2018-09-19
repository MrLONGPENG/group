package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class ParamException extends BaseException {
    //有参的构造方法
    public ParamException(String message){
        super(ResultUtil.CODE_REQUEST_FORMAT, message);
    }

}
