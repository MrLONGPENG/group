package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class ExistException extends BaseException {
    //有参的构造方法
    public ExistException(String message){
        super(ResultUtil.CODE_DATA_DUPLICATION,message);
    }

}
