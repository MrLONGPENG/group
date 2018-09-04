package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.to.OrderTO;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.objeck.UserVO;
import com.lveqia.cloud.zuul.service.SysUserService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final MapperFactory mapperFactory;
    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(MapperFactory mapperFactory, SysUserService sysUserService) {
        this.mapperFactory = mapperFactory;
        this.sysUserService = sysUserService;
    }

    @RequestMapping(value = "/info")
    public String info(){
        SysUser sysUser = sysUserService.getCurrInfo();
        if(sysUser == null) return ResultUtil.error(ResultUtil.CODE_NOT_AUTHORITY);
        mapperFactory.classMap(SysUser.class, UserVO.class).byDefault().register();
        return ResultUtil.success(mapperFactory.getMapperFacade().map(sysUser, UserVO.class));
    }

}
