package com.lveqia.cloud.zuul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class App {

    @Value("${message}")
    String msg;

    @ResponseBody
    @RequestMapping(value = "/")
    String index(){
        return "获取的参数值" + msg;
    }
}
