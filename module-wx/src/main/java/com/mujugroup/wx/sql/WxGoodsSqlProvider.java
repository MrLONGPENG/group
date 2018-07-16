package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 商品套餐详情表,SQL语句组装类
 * 类名:WxGoodsSqlProvider
 * @author leolaurel
 * 创建时间:20180712
 */
public class WxGoodsSqlProvider {

    public String insert(WxGoods wxGoods){
        return new SQL(){{
            INSERT_INTO("t_wx_goods");
            if(wxGoods.getId()!= null) VALUES("`id`", "#{id}");
            if(wxGoods.getName()!= null) VALUES("`name`", "#{name}");
            if(wxGoods.getPrice()!= null) VALUES("`price`", "#{price}");
            if(wxGoods.getFeeType()!= null) VALUES("`fee_type`", "#{feeType}");
            if(wxGoods.getDays()!= null) VALUES("`days`", "#{days}");
            if(wxGoods.getType()!= null) VALUES("`type`", "#{type}");
            if(wxGoods.getState()!= null) VALUES("`state`", "#{state}");
            if(wxGoods.getExplain()!= null) VALUES("`explain`", "#{explain}");
        }}.toString();
    }



    public String update(WxGoods wxGoods){
        return new SQL(){{
            UPDATE("t_wx_goods");
            if(wxGoods.getId()!= null) SET("`id` = #{id}");
            if(wxGoods.getName()!= null) SET("`name` = #{name}");
            if(wxGoods.getPrice()!= null) SET("`price` = #{price}");
            if(wxGoods.getFeeType()!= null) SET("`fee_type` = #{feeType}");
            if(wxGoods.getDays()!= null) SET("`days` = #{days}");
            if(wxGoods.getType()!= null) SET("`type` = #{type}");
            if(wxGoods.getState()!= null) SET("`state` = #{state}");
            if(wxGoods.getExplain()!= null) SET("`explain` = #{explain}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String findListByRelation(@Param("key") Integer key, @Param("kid") Integer kid){
        return new SQL(){ {
                SELECT("*");
                FROM("t_wx_goods A");
                INNER_JOIN("t_wx_relation B ON A.id = B.rid");
                WHERE("B.`type` = 1");// 指定商品套餐类型
                if(key != null) AND().WHERE("B.`key` = #{key}");
                if(kid != null) AND().WHERE("B.`kid` = #{kid}");
        }}.toString();
    }
}
