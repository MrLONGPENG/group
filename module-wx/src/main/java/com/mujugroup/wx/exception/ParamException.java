package com.mujugroup.wx.exception;

public class ParamException extends Exception {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    //有参的构造方法
    public ParamException(int code, String message){
        super(message);
        setCode(code);
    }

}
