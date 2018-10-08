package com.mujugroup.core.controller;


import com.lveqia.cloud.common.config.AuthFilter;
import com.lveqia.cloud.common.exception.ExistException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.util.StreamReaderDelegate;
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
    public String list(@ApiParam(hidden = true) int uid) {
        List<SelectVO> userAgentList = agentService.getAgentListByUid(uid);
        // uid+type=2  count >0
        List<SelectVO> userHospitalAgentList = agentService.getAgentHospitalByUid(uid);
        if (userHospitalAgentList != null && userHospitalAgentList.size() > 0) {
            userAgentList.add(new SelectVO(-1, "其他可选医院"));
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

    @ApiOperation(value = "添加代理商", notes = "代理商添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam(name = "name") String name, @RequestParam(name = "enabled") int enabled, HttpServletRequest request) {

        if (agentService.insertAgent(name, enabled) == 1) {
            return ResultUtil.success("添加成功");
        } else {
            return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "添加失败!");
        }

    }

    @ApiOperation(value = "编辑代理商", notes = "编辑代理商")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editAgent(@ApiParam(value = "选中的代理商ID", required = true) @PathVariable(value = "id") int id, HttpServletRequest request) {

        Agent agent = agentService.findById(id);
        if (agent != null) {
            return ResultUtil.success(agent);
        } else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }

    @ApiOperation(value = "修改代理商", notes = "代理商修改")
    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public String updateAgent(@ApiParam(value = "选中的代理商ID", required = true, hidden = true) @RequestParam(name = "id", required = true) int id,
                              @ApiParam(value = "代理商名称", required = false) @RequestParam(name = "name", required = false) String name,
                              @ApiParam(value = "是否启用该代理商", required = false) @RequestParam(name = "enabled", required = false) int enabled, HttpServletRequest request) {


        if (agentService.updateAgent(id,name,enabled) == 1) {
            return ResultUtil.success("修改成功");
        } else {
            return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "修改失败!");
        }
    }


    @ApiOperation(value = "删除代理商", notes = "删除代理商")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAgent(@ApiParam(value = "选中的代理商ID", required = true) @RequestParam(name = "id") int id, HttpServletRequest request) {

        try {
            if (agentService.deleteById(id) == 1) {
                return ResultUtil.success("删除成功");
            } else {
                return ResultUtil.code(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ExistException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    public String getAgentById(@PathVariable(name = "id") int id, HttpServletRequest request) {
        Agent agent = agentService.findById(id);
        if (agent != null) {
            return ResultUtil.success(agent);
        } else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }


}
