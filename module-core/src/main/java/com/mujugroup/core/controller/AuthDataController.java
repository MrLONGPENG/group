package com.mujugroup.core.controller;


import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.bo.TreeBO;
import com.mujugroup.core.objeck.vo.TreeVO;
import com.mujugroup.core.service.AuthDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @author leolaurel
 */

@RestController
@RequestMapping("/auth")
@Api(description = "数据权限相关接口")
public class AuthDataController {

    private AuthDataService authDataService;
    //private final Logger logger = LoggerFactory.getLogger(AuthDataController.class);

    @Autowired
    public AuthDataController(AuthDataService authDataService) {
        this.authDataService = authDataService;
    }

    @ApiOperation(value = "查询查询树结构（代理商、医院、科室）", notes = "组合多表，生成树形结构数据")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String tree(@ApiParam(hidden = true) int uid, @ApiParam(value = "userId") @RequestParam(value = "userId"

            , required = false, defaultValue = "0") int userId) {
        String result = userId == 0 ? ResultUtil.success(getTreeBOList(uid)) : ResultUtil.success(getTreeBOList(userId));
        return result;
    }

    @ApiOperation(value = "更新数据权限", notes = "更新数据权限")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String updateAuthData(@ApiParam(value = "数据权限") @RequestParam(value = "authData"
            , required = false) String[] authData, @RequestParam("uid") int uid) {
        int result = authDataService.updateAuthData(uid, authData);
        if (result > 0) {
            return ResultUtil.success("修改权限成功!");
        } else {
            return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "修改权限失败!");
        }
    }

    @ApiOperation(value = "获取选定用户的数据权限", notes = "获取选定用户的数据权限")
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public String getAuthDataById(@ApiParam(value = "userId") @RequestParam(value = "userId") int userId) {
        List<DBMap> authDataList = authDataService.getAuthData(userId);
        List<String> list = new ArrayList<String>();
        if (authDataList != null && authDataList.size() > 0) {
            for (DBMap dbMap : authDataList) {
                if (Integer.parseInt(dbMap.getKey()) == 1) {
                    list.add("AID" + dbMap.getValue());
                } else if (Integer.parseInt(dbMap.getKey()) == 2) {
                    list.add("HID" + dbMap.getValue());
                } else {
                    list.add("OID" + dbMap.getValue());
                }
            }
            return ResultUtil.success(list);
        } else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }

    //数据权限树
    private List<TreeVO> getTreeBOList(int id) {
        List<TreeBO> aidList = authDataService.getAgentAuthData(id);
        List<TreeBO> hidList = authDataService.getHospitalAuthData(id);
        if (aidList.size() == 0 && hidList.size() == 0) {
            List<TreeBO> allList = authDataService.getAllAgentList();
            return authDataService.treeBoToVo(allList);
        }
        if (hidList.size() > 0) {
            TreeBO treeBO = new TreeBO();
            treeBO.setId("AID0");
            treeBO.setName("其他可选医院");
            treeBO.setDisabled(true);
            treeBO.setChildren(authDataService.toJsonString(hidList));
            aidList.add(treeBO);
        }
        return authDataService.treeBoToVo(aidList);
    }

}
