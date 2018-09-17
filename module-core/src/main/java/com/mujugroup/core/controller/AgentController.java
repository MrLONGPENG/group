package com.mujugroup.core.controller;


import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 代理商信息
 * @author leolaurel
 */
@RestController
@RequestMapping("/agent")
@Api(description="代理商相关接口")
public class AgentController {

    private AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @ApiOperation(value="查询代理商列表", notes="查询代理商列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(){
        List<SelectVO> list = agentService.getAgentList();
        if(list != null) return ResultUtil.success(list);
        return  ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }


}
