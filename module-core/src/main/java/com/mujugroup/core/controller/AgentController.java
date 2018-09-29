package com.mujugroup.core.controller;


import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 代理商信息
 *
 * @author leolaurel
 */
@RestController
@RequestMapping("/agent")
@Api(description = "代理商相关接口")
public class AgentController {

    private AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @ApiOperation(value = "查询代理商列表", notes = "查询代理商列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(HttpServletRequest request) {
        UserInfo userInfo = AuthUtil.getUserInfo(request);
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_VALIDATION_FAIL);
        List<SelectVO> userAgentList = agentService.getAgentListByUid(userInfo.getId());
        // uid+type=2  count >0
        List<SelectVO> userHospitalAgentList=agentService.getAgentHospitalByUid(userInfo.getId());
        if (userHospitalAgentList!=null&&userHospitalAgentList.size()>0){
            userAgentList.add(new SelectVO(-1,"其他可选医院"));
        }

        if (userAgentList == null || userAgentList.size() == 0) {
            List<SelectVO> list = agentService.getTheAgentList();
            if (list != null) {
                return ResultUtil.success(list);
            } else {
                return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
            }
        } else {
            return ResultUtil.success(userAgentList);
        }
    }


}
