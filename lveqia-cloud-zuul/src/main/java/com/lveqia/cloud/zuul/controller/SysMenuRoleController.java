package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.zuul.service.SysMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sysMenuRole")
public class SysMenuRoleController {

    private SysMenuRoleService sysMenuRoleService;

    @Autowired
    public SysMenuRoleController(SysMenuRoleService sysMenuRoleService) {
        this.sysMenuRoleService = sysMenuRoleService;
    }

    @RequestMapping(value = "/test")
    public String test(){
        return sysMenuRoleService.test();
    }

}
