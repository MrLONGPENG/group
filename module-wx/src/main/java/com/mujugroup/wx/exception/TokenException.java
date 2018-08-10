package com.mujugroup.wx.exception;

import com.lveqia.cloud.common.ResultUtil;

public class TokenException extends Exception {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    //有参的构造方法
    public TokenException(){
        super("Token异常");
        setCode(ResultUtil.CODE_VALIDATION_FAIL);
    }

}
