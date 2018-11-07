package com.mujugroup.core.controller;


import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.bo.TreeBo;
import com.mujugroup.core.objeck.vo.TreeVo;
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
    public String tree(@ApiParam(value = "userId") @RequestParam(value = "userId", required = false
            , defaultValue = "0") int userId, @ApiParam(hidden = true) int uid) {
        List<TreeVo> list = getTreeBOList(userId==0 ? uid : userId);
        return list !=null ? ResultUtil.success(list) : ResultUtil.error(ResultUtil.CODE_DATA_AUTHORITY
                , "当前用户无数据权限，无法操作，请联系管理员！");
    }

    @ApiOperation(value = "更新数据权限", notes = "更新数据权限")
    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public String put(@RequestParam("userId") int userId, @ApiParam(value = "数据权限")
            @RequestParam(value = "authData", required = false) String[] authData) {
        // TODO 此处没有判断当前用户是否能更改当前权限
        int result = authDataService.updateAuthData(userId, authData);
        if (result > 0) {
            return ResultUtil.success("修改权限成功!");
        } else {
            return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "修改权限失败!");
        }
    }

    @ApiOperation(value = "获取选定用户的数据权限", notes = "获取选定用户的数据权限")
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public String getAuthDataById(@ApiParam(value = "userId") @RequestParam(value = "userId") int userId) {
        return ResultUtil.success(authDataService.getAuthDataList(userId));
    }

    //数据权限树
    private List<TreeVo> getTreeBOList(int id) {
        List<TreeBo> allList = new ArrayList<>();
        List<DBMap> auth = authDataService.getAuthData(id);
        if(auth!=null && auth.stream().anyMatch(dbMap -> CoreConfig.AUTH_DATA_ALL.equals(dbMap.getKey()))){
            allList.add(getTreeBo("ALL0","全部代理商",false, authDataService.getAllAgentList()));
            return authDataService.treeBoToVo(allList);
        }
        List<TreeBo> aidList = authDataService.getAgentAuthData(id);
        List<TreeBo> hidList = authDataService.getHospitalAuthData(id);
        if (aidList.size() == 0 && hidList.size() == 0) return null;
        if (hidList.size() > 0) {
            aidList.add(getTreeBo("AID0", "其他可选医院", true,  hidList));
        }
        return authDataService.treeBoToVo(aidList);
    }

    private TreeBo getTreeBo(String id, String name, boolean disabled, List<TreeBo> children) {
        TreeBo treeBO = new TreeBo();
        treeBO.setId(id);
        treeBO.setName(name);
        treeBO.setDisabled(disabled);
        treeBO.setChildren(authDataService.toJsonString(children));
        return treeBO;
    }

}
