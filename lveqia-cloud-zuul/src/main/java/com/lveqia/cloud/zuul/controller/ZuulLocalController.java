package com.lveqia.cloud.zuul.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RefreshScope
@RestController
public class ZuulLocalController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleError() {
        return "{\"code\":404,\"info\":\"无法找到相应的路径\"}";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    @ResponseBody
    @RequestMapping(value = "/")
    public String index(){
        return "{\"code\":200,\"info\":\"欢迎使用木巨微服务平台\"}";
    }
}
