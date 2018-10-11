package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.wx.mapper.WxGoodsMapper;
import com.mujugroup.wx.mapper.WxRelationMapper;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.objeck.vo.GoodsVo;
import com.mujugroup.wx.service.WxGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("wxGoodsService")
public class WxGoodsServiceImpl implements WxGoodsService {

    private final Logger logger = LoggerFactory.getLogger(WxGoodsServiceImpl.class);
    private final WxGoodsMapper wxGoodsMapper;
    private final WxRelationMapper wxRelationMapper;

    @Autowired
    public WxGoodsServiceImpl(WxGoodsMapper wxGoodsMapper, WxRelationMapper wxRelationMapper) {
        this.wxGoodsMapper = wxGoodsMapper;
        this.wxRelationMapper = wxRelationMapper;
    }

    @Override
    public WxGoods findById(Integer id) {
        return wxGoodsMapper.findById(id);
    }

    @Override
    public WxGoods getDefaultGoods(int type) {
        WxGoods wxGoods = new WxGoods();
        wxGoods.setType(type);
        wxGoods.setState(1);
        wxGoods.setFeeType("CNY");
        wxGoods.setExplain("代码默认数据");
        switch (type) {
            case 1:
                wxGoods.setName("限免");
                wxGoods.setDays(0);
                wxGoods.setPrice(0);
                break;
            case 2:
                wxGoods.setName("20元/天(限时五折)");
                wxGoods.setDays(1);
                wxGoods.setPrice(1000);
                break;
            case 3:
                wxGoods.setName("午休壹元体验");
                wxGoods.setDays(0);
                wxGoods.setPrice(100);
                break;
            case 4:
                wxGoods.setName("一次性被子");
                wxGoods.setDays(0);
                wxGoods.setPrice(2000);
                break;
        }
        return wxGoods;
    }

    /**
     * 排除指定类型，然后依次查询押金/套餐/午休/被子 等类型
     */
    @Override
    public List<WxGoods> findListExcludeType(int type, String aid, String hid, String oid) {
        List<WxGoods> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) { //TODO 暂时处理到午休时间
            if (i == type) continue;
            list.addAll(findList(i, aid, hid, oid));
        }
        return list;
    }


    @Override
    public List<WxGoods> findList(int type, String aid, String hid, String oid) {
        return findList(type, Integer.parseInt(aid), Integer.parseInt(hid), Integer.parseInt(oid));
    }

    @Override
    public List<WxGoods> findList(int type, int aid, int hid, int oid) {
        List<WxGoods> list = findListByXid(new int[]{0, aid, hid, oid}, type);
        if (list == null) {
            list = new ArrayList<>();
        } else if (list.size() == 0) {
            list.add(getDefaultGoods(type));
        }
        return list;
    }

    /**
     * 依次按照科室/医院/代理商/默认数据 查询
     */
    @Override
    public List<WxGoods> findListByXid(int[] ints, int type) {
        List<WxGoods> list = null;
        for (int i = 3; i > -1; i--) {
            if (i != ints[i] && ints[i] == 0) continue;
            list = queryList(i, ints[i], type);
            if (list != null && list.size() > 0) break;
        }
        return list;
    }

    @Override
    public List<GoodsVo> findRelationListByKid(int kid, int key) {
        return wxGoodsMapper.findRelationListByKid(kid,key);
    }

    @Override
    public List<WxGoods> queryList(int key, int kid, int type) {
        logger.debug("queryList key:{} kid:{} type:{}", key, kid, type);
        if (key == WxRelation.KEY_DEFAULT) kid = WxRelation.KID_DEFAULT;
        return wxGoodsMapper.findListByRelation(key, kid, type == 0 ? null : type);
    }


    @Override
    @Transactional
    public boolean update(int type, int key, int kid, int gid, String name, int price, int days
            , int state, String explain) throws ParamException {
        if (type < 1 || type > 4) throw new ParamException("当前只支持Type类型(1:押金 2:套餐 3:午休 4:被子)");
        if (state != 1 && state != 2) throw new ParamException("商品状态{state}只能为1:当前可用 2:敬请期待");
        if (type == 2 && days <= 0) throw new ParamException("商品类型为基本套餐时候,必须指定Days属性,最小数量为1天");
        if (key == WxRelation.KEY_DEFAULT && gid <= 0) throw new ParamException("默认数据无法新增，请更改外键类型或指定默认商品ID");
        if (gid > 0) { // 更新指定商品
            List<WxGoods> list = queryList(key, kid, type);
            WxGoods wxGoods = list.stream().filter(goods -> goods.getId() == gid).findFirst().orElseThrow(
                    () -> new ParamException("该商品类型中无法找到指定数据，请确认GID[" + gid + "]是否存在")
            );
            wxGoodsMapper.update(bindData(wxGoods, name, type, price, days, state, explain));
        } else {
            WxGoods wxGoods = new WxGoods();
            wxGoodsMapper.insert(bindData(wxGoods, name, type, price, days, state, explain));
            WxRelation relation = new WxRelation();
            relation.setKey(key);
            relation.setKid(kid);
            relation.setRid(wxGoods.getId());
            relation.setType(WxRelation.TYPE_GOODS);
            wxRelationMapper.insert(relation);
        }
        return true;
    }

    /**
     * 商品数据(对象)绑定或更新数据
     */
    private WxGoods bindData(WxGoods wxGoods, String name, int type, int price, int days, int state, String explain) {
        wxGoods.setName(name);
        wxGoods.setType(type);
        wxGoods.setPrice(price);
        wxGoods.setDays(days);
        wxGoods.setState(state);
        wxGoods.setFeeType("CNY");
        if (!StringUtil.isEmpty(explain)) wxGoods.setExplain(explain);
        return wxGoods;
    }

    @Override
    @Transactional
    public boolean delete(int type, int key, int kid, int gid) throws ParamException, BaseException {
        if (key == WxRelation.KEY_DEFAULT) throw new ParamException("默认数据无法删除");
        if (type < 1 || type > 4) throw new ParamException("当前只支持Type类型(1:押金 2:套餐 3:午休 4:被子)");
        List<WxGoods> list = queryList(key, kid, type);
        if (list != null && list.size() > 0) {
            list.stream().filter(goods -> goods.getId() == gid || gid == 0)
                    .mapToInt(WxGoods::getId).forEach(wxGoodsMapper::deleteById);
            boolean isDelete;
            if (gid == 0) {
                isDelete = wxRelationMapper.deleteByType(WxRelation.TYPE_GOODS, key, kid);
            } else {
                isDelete = wxRelationMapper.deleteByRid(WxRelation.TYPE_GOODS, gid);
            }
            if (isDelete) return true;
        }
        throw new BaseException(ResultUtil.CODE_NOT_FIND_DATA, "无数据可删除");
    }

    @Override
    @Transactional
    //商品添加
    public boolean add(int type, String name, int price, int days, int state, String explain) throws ParamException {
        WxGoods wxGoods = new WxGoods();
        return wxGoodsMapper.insert(bindData(wxGoods, name, type, price, days, state, explain));
    }

    @Override
    @Transactional
    //商品修改
    public boolean modify(int type, int key, int kid, int gid, String name, int price, int days, int state, String explain) throws ParamException {
        if (type < 1 || type > 4) throw new ParamException("当前只支持Type类型(1:押金 2:套餐 3:午休 4:被子)");
        if (state != 1 && state != 2) throw new ParamException("商品状态{state}只能为1:当前可用 2:敬请期待");
        if (type == 2 && days <= 0) throw new ParamException("商品类型为基本套餐时候,必须指定Days属性,最小数量为1天");
        List<WxGoods> list = queryList(key, kid, type);
        WxGoods wxGoods = list.stream().filter(goods -> goods.getId() == gid).findFirst().orElseThrow(
                () -> new ParamException("该商品类型中无法找到指定数据，请确认GID[" + gid + "]是否存在")
        );
       return   wxGoodsMapper.update(bindData(wxGoods, name, type, price, days, state, explain));
    }

}
