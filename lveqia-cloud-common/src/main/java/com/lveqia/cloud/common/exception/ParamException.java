package com.lveqia.cloud.common.exception;

import com.lveqia.cloud.common.util.ResultUtil;

public class ParamException extends BaseException {
    public static final String GOODS_INFO_ERROR = "商品信息错误";

    //有参的构造方法
    public ParamException(String message){
        super(ResultUtil.CODE_REQUEST_FORMAT, message);
    }

}
