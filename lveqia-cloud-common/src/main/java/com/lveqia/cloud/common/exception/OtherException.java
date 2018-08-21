package com.lveqia.cloud.common.exception;

public class OtherException extends Exception {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    //有参的构造方法
    public OtherException(int code, String message){
        super(message);
        setCode(code);
    }

}
