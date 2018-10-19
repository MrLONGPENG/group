package com.lveqia.cloud.zuul.config.auth;

import com.lveqia.cloud.common.util.ResultUtil;
import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {

    private int code;

    public AuthException(int code) {
        this(code, ResultUtil.getMessage(code));
    }

    public AuthException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
