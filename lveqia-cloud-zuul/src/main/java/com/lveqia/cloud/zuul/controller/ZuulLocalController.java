package com.lveqia.cloud.zuul.controller;

import com.lveqia.cloud.common.util.ResultUtil;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RefreshScope
@RestController
public class ZuulLocalController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleError() {
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_PATH);
    }

    @ResponseBody
    @RequestMapping(value = "/authority")
    public String authority(@RequestParam(value = "error", required = false) String error
            , @RequestParam(value = "logout", required = false) String logout){
        if (error != null) {
            return ResultUtil.error(ResultUtil.CODE_NOT_AUTHORITY, "登陆错误!");
        }
        if (logout != null) {
            return ResultUtil.success("成功退出!");
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_AUTHORITY);
    }


    @ResponseBody
    @RequestMapping(value = "/")
    public String index(){
        return ResultUtil.success("欢迎使用木巨微服务平台");
    }
}
