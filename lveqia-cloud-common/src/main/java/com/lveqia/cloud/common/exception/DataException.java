package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class DataException extends BaseException {
    //有参的构造方法
    public DataException(String message){
        super(ResultUtil.CODE_DATA_AUTHORITY, message);
    }

}
