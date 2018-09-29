package com.mujugroup.core.controller;


import com.google.gson.GsonBuilder;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.bo.TreeBO;
import com.mujugroup.core.service.AuthDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/auth")
public class AuthDataController {

    private AuthDataService authDataService;
    private final Logger logger = LoggerFactory.getLogger(AuthDataController.class);

    @Autowired
    public AuthDataController(AuthDataService authDataService) {
        this.authDataService = authDataService;
    }

    @ApiOperation(value = "查询查询树结构（代理商、医院、科室）", notes = "组合多表，生成树形结构数据")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String tree(HttpServletRequest request) {
        UserInfo userInfo = AuthUtil.getUserInfo(request);
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_VALIDATION_FAIL);
        List<TreeBO> aidList = authDataService.getAgentAuthData(userInfo.getId());
        List<TreeBO> hidList = authDataService.getHospitalAuthData(userInfo.getId());
        if (aidList.size() == 0 && hidList.size() == 0) {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
        if (hidList.size() > 0) {
            TreeBO treeBO = new TreeBO();
            treeBO.setId("AID0");
            treeBO.setName("其他可选医院");
            treeBO.setDisabled(true);
            treeBO.setChildren(authDataService.toJsonString(hidList));
            aidList.add(treeBO);
        }

        return ResultUtil.success(authDataService.treeBoToVo(aidList));

    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String updateAuthData(@ApiParam(value = "数据权限") @RequestParam(value = "authDatas", required = false) String[] authDatas,
                                 @RequestParam("uid") int uid,HttpServletRequest request) {
        UserInfo userInfo = AuthUtil.getUserInfo(request);
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_VALIDATION_FAIL);
        int result = authDataService.updateAuthData(uid, authDatas);
        if (result > 0) {
            return ResultUtil.success("修改权限成功!");
        } else {
            return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "修改权限失败!");
        }
    }
}
