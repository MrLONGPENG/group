package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class BaseException extends Exception {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    //有参的构造方法
    public BaseException(int code){
        this(code, ResultUtil.getMessage(code));
    }
    //有参的构造方法
    public BaseException(int code, String message){
        super(message);
        setCode(code);
    }

}
