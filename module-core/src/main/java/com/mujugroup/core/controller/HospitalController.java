package com.mujugroup.core.controller;


import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.Hospital.HospitalVo;
import com.mujugroup.core.objeck.vo.Hospital.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.HospitalService;
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
@RequestMapping("/hospital")
@Api(description = "医院相关接口")
public class HospitalController {

    private HospitalService hospitalService;

    @Autowired
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @ApiOperation(value = "查询医院列表", notes = "查询医院列表，可模糊匹配医院名字")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@ApiParam(value = "代理商ID") @RequestParam(name = "aid", required = false,defaultValue = "0") int aid
            , @ApiParam(value = "模糊名字") @RequestParam(name = "name", required = false) String name, @ApiParam(hidden = true) int uid) {

        if (aid == -1) {
            return ResultUtil.success(hospitalService.getHospitalListByUid(CoreConfig.AUTH_DATA_HOSPITAL
                    , uid));
        } else if (aid == 0) {
            List<SelectVO> allList = hospitalService.getHospitalListByUid(CoreConfig.AUTH_DATA_HOSPITAL
                    , uid);
            List<SelectVO> secList = hospitalService.getAgentHospitalListByUid(CoreConfig.AUTH_DATA_AGENT
                    , uid);
            if (allList != null) {
                allList.addAll(secList);
            } else {
                allList = secList;
            }
            if (allList != null && allList.size() > 0) return ResultUtil.success(allList);
        }
        List<SelectVO> list = hospitalService.getHospitalList(aid, name);
        if (list != null) return ResultUtil.success(list);
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

    @ApiOperation(value = "添加医院", notes = "添加医院")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addHospital(@ApiParam(hidden = true) int uid, @ModelAttribute HospitalVo hospitalVo) {
        try {
            if (hospitalService.add(uid, hospitalVo)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "编辑医院", notes = "编辑医院")
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
    public String modifyHospital(@PathVariable(value = "id")String id,@ModelAttribute PutVo hospitalPutVo) {
        try{
            if (hospitalService.modify(id,hospitalPutVo)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        }catch (ParamException e){

            return ResultUtil.error(e.getCode(), e.getMessage());
        }

    }

    @ApiOperation(value = "删除医院", notes = "删除医院")
    @RequestMapping(value = "/delete/{hid}", method = RequestMethod.POST)
    public String removeHospital(@ApiParam(value = "医院ID") @PathVariable(value = "hid") String hid) {
        try {
            if (hospitalService.remove(hid)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }


}
