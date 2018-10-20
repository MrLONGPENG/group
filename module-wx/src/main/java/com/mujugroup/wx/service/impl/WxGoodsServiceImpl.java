package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.wx.mapper.WxGoodsMapper;
import com.mujugroup.wx.mapper.WxRelationMapper;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.objeck.vo.GoodsItemVo;
import com.mujugroup.wx.objeck.vo.GoodsVo;
import com.mujugroup.wx.service.WxGoodsService;
import com.mujugroup.wx.service.feign.ModuleCoreService;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("wxGoodsService")
public class WxGoodsServiceImpl implements WxGoodsService {

    private final Logger logger = LoggerFactory.getLogger(WxGoodsServiceImpl.class);
    private final WxGoodsMapper wxGoodsMapper;
    private final WxRelationMapper wxRelationMapper;
    private final ModuleCoreService moduleCoreService;
    private final MapperFactory mapperFactory;

    @Autowired
    public WxGoodsServiceImpl(WxGoodsMapper wxGoodsMapper, WxRelationMapper wxRelationMapper, ModuleCoreService moduleCoreService, MapperFactory mapperFactory) {
        this.wxGoodsMapper = wxGoodsMapper;
        this.wxRelationMapper = wxRelationMapper;
        this.moduleCoreService = moduleCoreService;
        this.mapperFactory = mapperFactory;
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
        for (int i = ints.length - 1; i > -1; i--) {
            if (i != ints[i] && ints[i] == 0) continue;
            list = queryList(i, ints[i], type);
            if (list != null && list.size() > 0) break;
        }
        return list;
    }


    @Override
    public List<WxGoods> queryList(int key, int kid, int type) {
        logger.debug("queryList key:{} kid:{} type:{}", key, kid, type);
        if (key == WxRelation.KEY_DEFAULT) kid = WxRelation.KID_DEFAULT;
        return wxGoodsMapper.findListByRelation(key, kid, type == 0 ? null : type);
    }


    @Override
    @Transactional
    public boolean add(int key, int kid, String name, double price, int days, String explain) throws ParamException {
        return update(WxGoods.TYPE_NIGHT, key, kid, 0, name, price, days, 1, explain);
    }

    @Override
    @Transactional
    public boolean update(int type, int key, int kid, int gid, String name, double price, int days
            , int state, String explain) throws ParamException {
        if (type < 1 || type > 4) throw new ParamException("当前只支持Type类型(1:押金 2:套餐 3:午休 4:被子)");
        if (state != 1 && state != 2) throw new ParamException("商品状态{state}只能为1:当前可用 2:敬请期待");
        if (type == 2 && days <= 0) throw new ParamException("商品类型为基本套餐时候,必须指定Days属性,最小数量为1天");
        if (type != 2 && days != 0) throw new ParamException("套餐天数,仅仅当Type为2的情况有效，其他为0");
        if (type == 1 || type == 4) throw new ParamException("当前只支持Type类型(2:套餐 3:午休)");
        if (key == WxRelation.KEY_DEFAULT && gid <= 0) throw new ParamException("默认数据无法新增，请更改外键类型或指定默认商品ID");
        if (!priceRegex(String.valueOf(price))) throw new ParamException("价格输入有误");
        int thePrice = formatDoubleToInt(price, 100.0);
        if (gid > 0) { // 更新指定商品
            List<WxGoods> list = queryList(key, kid, type);
            WxGoods wxGoods = list.stream().filter(goods -> goods.getId() == gid).findFirst().orElseThrow(
                    () -> new ParamException("该商品类型中无法找到指定数据，请确认GID[" + gid + "]是否存在")
            );

            wxGoodsMapper.update(bindData(wxGoods, name, type, thePrice, days, state, explain));
        } else {
            WxGoods wxGoods = new WxGoods();
            wxGoodsMapper.insert(bindData(wxGoods, name, type, thePrice, days, state, explain));
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

    private WxGoods bindModel(int gid, String name, int type, int price, int days, int state, String explain) {
        WxGoods wxGoods = new WxGoods();
        if (gid > 0) wxGoods.setId(gid);
        bindData(wxGoods, name, type, price, days, state, explain);
        return wxGoods;
    }

    private WxRelation bindRelation(int key, int kid, int rid, int type) {
        WxRelation wxRelation = new WxRelation();
        wxRelation.setType(type);
        wxRelation.setKid(kid);
        wxRelation.setRid(rid);
        wxRelation.setKey(key);
        return wxRelation;
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
    public GoodsVo getGoodsVoList(int aid, int hid) {
        GoodsVo goodsVo = new GoodsVo(hid, WxRelation.KEY_HOSPITAL);
        //得到医院的午休商品
        List<WxGoods> noonGoodsList = queryList(WxRelation.KEY_HOSPITAL, hid, WxGoods.TYPE_MIDDAY);
        String hospitalName = moduleCoreService.getHospitalName(hid);
        goodsVo.setName(hospitalName);
        if (noonGoodsList != null && noonGoodsList.size() > 0) {
            //设置午休类型为自定义
            goodsVo.setNoon_type(1);
            goodsVo.setGoods(toGoodsVo(noonGoodsList).get(0));
        } else {
            //获取默认的午休商品
            List<WxGoods> defaultNoonGoodsList = findListByXid(new int[]{0, aid}, WxGoods.TYPE_MIDDAY);
            goodsVo.setNoon_type(0);
            goodsVo.setGoods(toGoodsVo(defaultNoonGoodsList).get(0));
        }
        //获取医院套餐商品
        List<WxGoods> comboGoodsList = queryList(WxRelation.KEY_HOSPITAL, hid, WxGoods.TYPE_NIGHT);
        if (comboGoodsList != null && comboGoodsList.size() > 0) {
            goodsVo.setCombo_type(1);

            goodsVo.setList(toGoodsVo(comboGoodsList));
        } else {
            //获取默认的套餐商品
            List<WxGoods> defaultComboGoodsList = findListByXid(new int[]{0, aid}, WxGoods.TYPE_NIGHT);
            goodsVo.setCombo_type(0);
            goodsVo.setList(toGoodsVo(defaultComboGoodsList));
        }
        //远程跨服务调用，获取当前所选医院下的科室ID,科室名称
        Map<Integer, String> oidMap = moduleCoreService.findOidByHid(Integer.toString(hid));
        List<GoodsVo> goodsList = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : oidMap.entrySet()) {
            GoodsVo departmentGoods = getDepartmentGoods(entry.getKey(), entry.getValue(), goodsVo);
            goodsList.add(departmentGoods);
        }
        goodsVo.setChildren(goodsList);
        return goodsVo;
    }

    //得到科室的商品
    private GoodsVo getDepartmentGoods(int oid, String name, GoodsVo vo) {
        GoodsVo goodsVo = new GoodsVo(oid, WxRelation.KEY_DEPARTMENT);
        goodsVo.setName(name);
        List<WxGoods> departmentNoonGoods = queryList(WxRelation.KEY_DEPARTMENT, oid, WxGoods.TYPE_MIDDAY);
        if (departmentNoonGoods != null && departmentNoonGoods.size() > 0) {
            goodsVo.setNoon_type(1);
            goodsVo.setGoods(toGoodsVo(departmentNoonGoods).get(0));
        } else {
            goodsVo.setNoon_type(0);
            goodsVo.setGoods(vo.getGoods());
        }
        List<WxGoods> departmentComboGoods = queryList(WxRelation.KEY_DEPARTMENT, oid, WxGoods.TYPE_NIGHT);
        if (departmentComboGoods != null && departmentComboGoods.size() > 0) {
            goodsVo.setCombo_type(1);
            goodsVo.setList(toGoodsVo(departmentComboGoods));
        } else {
            goodsVo.setCombo_type(0);
            goodsVo.setList(vo.getList());
        }
        return goodsVo;
    }

    private List<GoodsItemVo> toGoodsVo(List<WxGoods> list) {
        mapperFactory.classMap(WxGoods.class, GoodsItemVo.class)
                .fieldMap("price").converter("rmbPriceConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(list, GoodsItemVo.class);
    }

    @Transactional
    @Override
    public boolean insertOrModify(int type, int noon_type, int combo_type, int key, int kid, int gid, String name, double price, int days
            , int state, String explain) throws ParamException {
        if (noon_type == 1 && gid <= 0) throw new ParamException("自定义午休类型ID不能小于等于0");
        if (combo_type == 1 && gid <= 0) throw new ParamException("自定义套餐类型ID不能小于等于0");
        if (!priceRegex(String.valueOf(price))) throw new ParamException("价格输入有误");
        int thePrice = formatDoubleToInt(price, 100.0);
        List<WxGoods> goodsList = queryList(key, kid, type);
        //修改自定义商品类型
        if ((noon_type == 1 && type == WxGoods.TYPE_MIDDAY)
                || (combo_type == 1 && type == WxGoods.TYPE_NIGHT)) {
            if (goodsList == null||goodsList.size()<=0)
                throw new ParamException("请确认输入参数是否正确");
            if(goodsList.stream().filter(goods->goods.getId()==gid).count()<=0){
                throw new ParamException("请确认输入gid是否正确");
            }
            WxGoods model = bindModel(gid, name, type, thePrice, days, state, explain);
            return wxGoodsMapper.update(model);
        } else {
            //添加默认商品类型
            if (goodsList != null && goodsList.size() > 0)
                throw new ParamException("请确认商品类型是否正确, 当前存在定义类型");
            WxGoods defaultModel = bindModel(0, name, type, thePrice, days, state, explain);
            boolean result = wxGoodsMapper.insert(defaultModel);
            WxRelation wxRelation = bindRelation(key, kid, defaultModel.getId(), WxRelation.TYPE_GOODS);
            result &= wxRelationMapper.insert(wxRelation);
            return result;
        }
    }

    @Override
    @Transactional
    //商品添加(不涉及Relation关系的添加)
    public boolean add(int type, String name, int price, int days, int state, String explain) throws ParamException {
        if (type < 1 || type > 4) throw new ParamException("当前只支持Type类型(1:押金 2:套餐 3:午休 4:被子)");
        if (state != 1 && state != 2) throw new ParamException("商品状态{state}只能为1:当前可用 2:敬请期待");
        if (type == 2 && days <= 0) throw new ParamException("商品类型为基本套餐时候,必须指定Days属性,最小数量为1天");
        if (type != 2 && days != 0) throw new ParamException("套餐天数,仅仅当Type为2的情况有效，其他为0");
        WxGoods wxGoods = new WxGoods();
        return wxGoodsMapper.insert(bindData(wxGoods, name, type, price, days, state, explain));
    }

    @Override
    @Transactional
    //商品修改
    public boolean modify(int type, int gid, String name, int price, int days, int state, String explain) throws ParamException {
        if (type < 1 || type > 4) throw new ParamException("当前只支持Type类型(1:押金 2:套餐 3:午休 4:被子)");
        if (state != 1 && state != 2) throw new ParamException("商品状态{state}只能为1:当前可用 2:敬请期待");
        if (type == 2 && days <= 0) throw new ParamException("商品类型为基本套餐时候,必须指定Days属性,最小数量为1天");
        if (type != 2 && days != 0) throw new ParamException("套餐天数,仅仅当Type为2的情况有效，其他为0");
        //判断该商品是否存在
        WxGoods wxGoods = findById(gid);
        if (wxGoods != null) {
            return wxGoodsMapper.update(bindData(wxGoods, name, type, price, days, state, explain));
        } else {
            throw new ParamException("该商品类型中无法找到指定数据，请确认GID[" + gid + "]是否存在");
        }
    }

    private boolean priceRegex(String strPrice) {
        String regex = "^[0-9]+([.]{1}[0-9]{1,2})?$";
        return strPrice.matches(regex);
    }

    private static int formatDoubleToInt(double firNum, double secNum) {
        BigDecimal bigFir = new BigDecimal(Double.valueOf(firNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal bigSec = new BigDecimal(Double.valueOf(secNum));
        return bigFir.multiply(bigSec).intValue();
    }
}
