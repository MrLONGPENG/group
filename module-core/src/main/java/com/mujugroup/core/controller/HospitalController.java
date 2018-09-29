package com.mujugroup.core.controller;


import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.AuthUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.model.AuthData;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.HospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 代理商信息
 * @author leolaurel
 */
@RestController
@RequestMapping("/hospital")
@Api(description="医院相关接口")
public class HospitalController {

    private HospitalService hospitalService;

    @Autowired
    public HospitalController( HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @ApiOperation(value="查询医院列表", notes="查询医院列表，可模糊匹配医院名字")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@ApiParam(value="代理商ID")@RequestParam(name="aid", required=true, defaultValue="0") int aid
            , @ApiParam(value="模糊名字")@RequestParam(name="name", required=false) String name, HttpServletRequest request){
        UserInfo userInfo= AuthUtil.getUserInfo(request);
        if(userInfo == null) return ResultUtil.error(ResultUtil.CODE_VALIDATION_FAIL);
        if(aid == -1){
            // uid  auth+hosot pype =2
            List<SelectVO> hospitalListFir=hospitalService.getHospitalListByUid(AuthData.hospitalType,userInfo.getId());
        }else if(aid == 0){
            // uid  auht +hos  pype 2\
            // +  auht +hoso pyoe+1
            List<SelectVO> hospitalListFir=hospitalService.getHospitalListByUid(AuthData.hospitalType,userInfo.getId());
            List<SelectVO> hospitalListSec=hospitalService.getAgentHospitalListByUid(AuthData.agentType,userInfo.getId());
            if ((hospitalListFir!=null&&hospitalListFir.size()>0)||(hospitalListSec!=null&&hospitalListSec.size()>0)){
               hospitalListFir.addAll(hospitalListSec);
                return  ResultUtil.success(hospitalListFir);
            }
        }
        List<SelectVO> list = hospitalService.getHospitalList(aid, name);
        if(list != null) return ResultUtil.success(list);
        return  ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }
}
