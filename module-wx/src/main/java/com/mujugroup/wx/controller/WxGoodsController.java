package com.mujugroup.wx.controller;


import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.objeck.vo.GoodsVo;
import com.mujugroup.wx.service.WxGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.spi.ResolveResult;
import java.util.List;


@RestController
@RequestMapping("/goods")
@Api(description = "医院商品业务接口")
public class WxGoodsController {
    private final Logger logger = LoggerFactory.getLogger(WxGoodsController.class);
    private WxGoodsService wxGoodsService;

    @Autowired
    public WxGoodsController(WxGoodsService wxGoodsService) {
        this.wxGoodsService = wxGoodsService;
    }


    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ApiOperation(value = "医院商品查询接口", notes = "根据类型查询医院套餐商品等信息,未找到返回默认数据")
    public String find(@ApiParam(value = "类型(1:押金 2:套餐 3:午休 4:被子)", required = true) @RequestParam(name = "type"
            , defaultValue = "2") int type, @ApiParam(value = "代理商ID") @RequestParam(name = "aid", defaultValue = "0")
                               int aid, @ApiParam(value = "医院ID") @RequestParam(name = "hid", defaultValue = "0") int hid, @ApiParam(value =
            "科室ID") @RequestParam(name = "oid", defaultValue = "0") int oid) {
        logger.debug("find type:{} aid:{} hid:{} oid:{}", type, aid, hid, oid);
        if (type < 1 || type > 4) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "Type:暂时只支持[1,2,3,4]");
        return ResultUtil.success(wxGoodsService.findList(type, aid, hid, oid));
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value = "医院商品查询接口", notes = "根据类型查询医院套餐商品等信息,未找到返回空")
    public String query(@ApiParam(value = "类型(1:押金 2:套餐 3:午休 4:被子)", required = true) @RequestParam(name = "type"
            , defaultValue = "2") int type, @ApiParam(value = "外键类型(0:默认数据 1:代理商 2:医院 3:科室 4:其他)"
            , required = true) @RequestParam(name = "key") int key, @ApiParam(value = "外键ID"
            , required = true) @RequestParam(name = "kid") int kid) {
        logger.debug("query type:{} key:{} kid:{}", type, key, kid);
        List<WxGoods> list = wxGoodsService.queryList(key, kid, type);
        if (list != null && list.size() > 0) {
            return ResultUtil.success(list);
        } else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "医院商品新增或更新接口", notes = "根据类型新增或更新商品信息")
    public String update(@ApiParam(value = "类型(1:押金 2:套餐 3:午休 4:被子)", required = true) @RequestParam(name = "type"
            , defaultValue = "2") int type, @ApiParam(value = "外键类型(0:默认数据 1:代理商 2:医院 3:科室 4:其他)"
            , required = true) @RequestParam(name = "key") int key, @ApiParam(value = "外键ID", required = true)
                         @RequestParam(name = "kid") int kid, @ApiParam(value = "商品ID，大于O为指定更新,为零或不填为新增，默认为0")
                         @RequestParam(name = "gid", defaultValue = "0") int gid, @ApiParam(value = "商品名字", required = true)
                         @RequestParam(name = "name") String name, @ApiParam(value = "商品价格", required = true) @RequestParam(name
            = "price") int price, @ApiParam(value = "套餐天数,仅仅当Type为2的情况有效，其他为0") @RequestParam(name
            = "days", defaultValue = "0") int days, @ApiParam(value = "商品状态 1:当前可用 2:敬请期待") @RequestParam(name
            = "state", defaultValue = "1") int state, @ApiParam(value = "此属性使用说明, 非必须") @RequestParam(name
            = "explain", required = false) String explain) {
        logger.debug("update type:{} key:{} kid:{}", type, key, kid);
        try {
            if (wxGoodsService.update(type, key, kid, gid, name, price, days, state, explain)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (NumberFormatException e) {
            return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "医院商品删除接口", notes = "根据类型删除商品信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@ApiParam(value = "类型(1:押金 2:套餐 3:午休 4:被子)", required = true) @RequestParam(name = "type"
            , defaultValue = "2") int type, @ApiParam(value = "外键类型(0:默认数据 1:代理商 2:医院 3:科室 4:其他)"
            , required = true) @RequestParam(name = "key") int key, @ApiParam(value = "外键ID", required = true)
                         @RequestParam(name = "kid") int kid, @ApiParam(value = "商品ID,存在删除指定商品，不存在删除该类商品")
                         @RequestParam(name = "gid", defaultValue = "0") int gid) {
        try {
            if (wxGoodsService.delete(type, key, kid, gid)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (BaseException e) {
            return ResultUtil.code(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "获取医院商品列表", notes = "获取医院商品列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String getGoodsList(@ApiParam(value = "代理商ID") @RequestParam(name = "aid") int aid
            , @ApiParam(value = "医院ID") @RequestParam(name = "hid") int hid ) {
        return ResultUtil.success(wxGoodsService.getGoodsVoList(aid, hid));

    }

    @ApiOperation(value = "修改或新增商品接口", notes = "修改或新增商品接口")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyGoods(@ApiParam(value = "外键类型") @RequestParam(value = "key") int key
            , @ApiParam(value = "商品类型", required = true) @RequestParam(name = "type", defaultValue = "2") int type
            , @ApiParam(value = "外键ID", required = true) @RequestParam(name = "kid") int kid
            , @ApiParam(value = "午休类型(0:默认，1：自定义)", required = true) @RequestParam(name = "noon_type", defaultValue = "0") int noon_type
            , @ApiParam(value = "套餐类型(0:默认，1：自定义)", required = true) @RequestParam(name = "combo_type", defaultValue = "0") int combo_type
            , @ApiParam(value = "商品ID") @RequestParam(name = "gid", defaultValue = "0") int gid
            , @ApiParam(value = "商品名字", required = true) @RequestParam(name = "name") String name
            , @ApiParam(value = "商品价格", required = true) @RequestParam(name = "price") int price
            , @ApiParam(value = "套餐天数,仅仅当Type为2的情况有效，其他为0") @RequestParam(name = "days", defaultValue = "0") int days
            , @ApiParam(value = "商品状态 1:当前可用 2:敬请期待") @RequestParam(name = "state", defaultValue = "1") int state
            , @ApiParam(value = "此属性使用说明, 非必须") @RequestParam(name = "explain", required = false) String explain
    ) {
        try {
            if (wxGoodsService.insertOrModify(type, noon_type, combo_type, key, kid, gid, name, price, days, state, explain)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ParamException e) {
            return ResultUtil.code(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "新增商品接口", notes = "修改或新增商品接口")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addGoods(@ApiParam(value = "类型(1:押金 2:套餐 3:午休 4:被子)", required = true) @RequestParam(name = "type"
            , defaultValue = "2") int type, @ApiParam(value = "外键类型(0:默认数据 1:代理商 2:医院 3:科室 4:其他)"
            , required = true) @RequestParam(name = "key") int key, @ApiParam(value = "外键ID", required = true)@RequestParam(name = "kid") int kid
            , @ApiParam(value = "商品名字", required = true) @RequestParam(name = "name") String name
            , @ApiParam(value = "商品价格", required = true) @RequestParam(name = "price") int price
            , @ApiParam(value = "此属性使用说明, 非必须") @RequestParam(name = "explain", required = false) String explain
    ) {
        logger.debug("update type:{} key:{} kid:{}", type, key, kid);
        try {
            if (wxGoodsService.add(type, key, kid, name, price, explain)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (NumberFormatException e) {
            return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

}

