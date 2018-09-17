package com.mujugroup.lock.controller;

import com.lveqia.cloud.common.util.ResultUtil;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleError() {
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_PATH);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
