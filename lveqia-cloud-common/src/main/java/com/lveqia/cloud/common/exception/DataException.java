package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class DataException extends BaseException {

    public static final String NO_AUTHORITY = "当前用户无数据权限，请联系管理员！";
    //有参的构造方法
    public DataException(String message){
        super(ResultUtil.CODE_DATA_AUTHORITY, message);
    }

}
