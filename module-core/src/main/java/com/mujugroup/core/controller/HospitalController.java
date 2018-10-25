package com.mujugroup.core.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.hospital.AddVo;
import com.mujugroup.core.objeck.vo.hospital.ListVo;
import com.mujugroup.core.objeck.vo.hospital.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.HospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

    @ApiOperation(value = "查询医院下拉列表", notes = "查询医院下拉列表，可模糊匹配医院名字")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String list(@ApiParam(value = "代理商ID") @RequestParam(name = "aid", required = false, defaultValue = "0") int aid
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
            List<SelectVO> list = hospitalService.getHospitalList(uid, aid, name);
            if (list != null) return ResultUtil.success(list);
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

    @ApiOperation(value = "添加医院", notes = "添加医院")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addHospital(@ApiParam(hidden = true) int uid, @Validated @ModelAttribute AddVo addVo) throws BaseException {
        if (hospitalService.add(uid, addVo)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "编辑医院", notes = "编辑医院")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public String modifyHospital(@ApiParam(hidden = true) int uid, @Validated @ModelAttribute PutVo hospitalPutVo) throws BaseException {
        if (hospitalService.modify(uid, hospitalPutVo)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "删除医院", notes = "删除医院")
    @RequestMapping(value = "/delete/{hid}", method = RequestMethod.POST)
    public String removeHospital(@ApiParam(hidden = true) int uid, @ApiParam(value = "医院ID") @PathVariable(value = "hid") String hid) throws BaseException {
        if (hospitalService.remove(uid, hid)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "医院分页列表", notes = "医院分页列表,支持省市,名称模糊查询")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String findAll(@ApiParam(value = "当前页") @RequestParam(name = "pageNum", required = false
            , defaultValue = "1") int pageNum, @ApiParam(value = "每页显示") @RequestParam(name = "pageSize"
            , required = false, defaultValue = "10") int pageSize
            , @ApiParam(hidden = true) int uid
            , @ApiParam(value = "代理商编号") @RequestParam(value = "aid", required = false, defaultValue = "0") int aid
            , @ApiParam(value = "医院名称") @RequestParam(value = "name", required = false, defaultValue = "") String name
            , @ApiParam(value = "省份编号") @RequestParam(value = "provinceId", required = false, defaultValue = "0") int provinceId
            , @ApiParam(value = "城市编号") @RequestParam(value = "cityId", required = false, defaultValue = "0") int cityId
    ) throws BaseException {

        PageHelper.startPage(pageNum, pageSize);
        List<ListVo> list = hospitalService.findAll(uid, aid, name, provinceId, cityId);
        if (list != null && list.size() > 0) {
            return ResultUtil.success(list, PageInfo.of(list));
        } else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }


}
