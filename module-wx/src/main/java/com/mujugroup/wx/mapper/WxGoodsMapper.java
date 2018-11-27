package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.objeck.vo.GoodsVo;
import com.mujugroup.wx.sql.WxGoodsSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品套餐详情表,数据库操作接口类
 * 类名:WxGoodsMapper
 *
 * @author leolaurel
 * 创建时间:20180712
 */
@Mapper
@Component(value = "wxGoodsMapper")
public interface WxGoodsMapper {

    @InsertProvider(type = WxGoodsSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    boolean insert(WxGoods wxGoods);

    @UpdateProvider(type = WxGoodsSqlProvider.class, method = "update")
    boolean update(WxGoods wxGoods);

    @Delete("delete from t_wx_goods where id= #{id}")
    boolean deleteById(int id);

    @Update("UPDATE t_wx_goods SET `state`=-1 WHERE `id`= #{id}")
    boolean modifyState(@Param("id") int id);

    @Select("SELECT * FROM t_wx_goods WHERE id = #{id}")
    @Results(id = "wxGoods", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "name", property = "name", javaType = String.class)
            , @Result(column = "price", property = "price", javaType = Integer.class)
            , @Result(column = "fee_type", property = "feeType", javaType = String.class)
            , @Result(column = "days", property = "days", javaType = Integer.class)
            , @Result(column = "type", property = "type", javaType = Integer.class)
            , @Result(column = "state", property = "state", javaType = Integer.class)
            , @Result(column = "explain", property = "explain", javaType = String.class)
    })
    WxGoods findById(Integer id);

    @ResultMap("wxGoods")
    @Select("SELECT * FROM t_wx_goods limit 1000")
    List<WxGoods> findListAll();

    @ResultMap("wxGoods")
    @SelectProvider(type = WxGoodsSqlProvider.class, method = "findListByRelation")
    List<WxGoods> findListByRelation(@Param("key") Integer key, @Param("kid") Integer kid, @Param("type") Integer type);
}