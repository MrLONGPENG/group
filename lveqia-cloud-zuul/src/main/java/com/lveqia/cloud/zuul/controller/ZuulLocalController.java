package com.lveqia.cloud.zuul.controller;

import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;


@RefreshScope
@RestController
public class ZuulLocalController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    //private static final String info = "Requested path %s with result %s";
    private final Logger logger = LoggerFactory.getLogger(ZuulLocalController.class);
    private final ErrorAttributes errorAttributes;


    @Autowired
    public ZuulLocalController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleError(WebRequest webRequest){
        Map<String, Object> map =errorAttributes.getErrorAttributes(webRequest, true);
        String trace = (String) map.get("trace");
        if(!StringUtil.isEmpty(trace)) {
            logger.error("status:{} error:{} message:{}", map.get("status") ,map.get("error"), map.get("message"));
        }
        int code = map.containsKey("status") ? (int) map.get("status") : 500;
        return ResultUtil.error(code == 404 ? ResultUtil.CODE_NOT_FIND_PATH : ResultUtil.CODE_UNKNOWN_ERROR, map);
    }

    @ResponseBody
    @RequestMapping(value = "/authority")
    public String authority(@RequestParam(value = "error", required = false) String error
            , @RequestParam(value = "logout", required = false) String logout){
        if (error != null) {
            return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID, "登陆错误!");
        }
        if (logout != null) {
            return ResultUtil.success("成功退出!");
        }
        return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
    }


    @ResponseBody
    @RequestMapping(value = "/")
    public String index(){
        return ResultUtil.success("欢迎使用木巨微服务平台");
    }
}
