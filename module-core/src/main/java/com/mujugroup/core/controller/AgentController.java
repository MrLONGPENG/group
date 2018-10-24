package com.mujugroup.core.controller;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.agent.AgentVo;
import com.mujugroup.core.objeck.vo.agent.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        try {
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
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }

    }

    @ApiOperation(value = "添加代理商", notes = "代理商添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ApiParam(value = "uid", hidden = true) int uid, @ModelAttribute AgentVo agentVo) {

        try {
            if (agentService.insertAgent(uid, agentVo)) {
                return ResultUtil.success("添加成功");
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "修改代理商", notes = "代理商修改")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public String updateAgent(@ApiParam(hidden = true) String uid, @ModelAttribute PutVo agentPutVo) {
        try {
            if (agentService.updateAgent(uid, agentPutVo)) {
                return ResultUtil.success("修改成功");
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }


    }

    @ApiOperation(value = "删除代理商", notes = "删除代理商")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAgent(@ApiParam(hidden = true) String uid, @ApiParam(value = "选中的代理商ID", required = true) @RequestParam(name = "id") String id) {
        try {
            if (agentService.deleteById(uid, id)) {
                return ResultUtil.success("删除成功");
            } else {
                return ResultUtil.code(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "代理商列表", notes = "可通过名称模糊匹配")
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String findAgentList(@ApiParam(hidden = true) String uid, @ApiParam(value = "name") @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        try {
            List<Agent> agentList = agentService.findAll(uid, name);
            if (agentList != null && agentList.size() > 0) {
                return ResultUtil.success(agentList);
            } else {
                return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
            }
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }

    }


}
