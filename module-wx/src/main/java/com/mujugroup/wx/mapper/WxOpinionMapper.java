package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxOpinion;
import com.mujugroup.wx.sql.WxOpinionSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 意见反馈表,数据库操作接口类
 * 类名:WxOpinionMapper
 * @author leolaurel
 * 创建时间:20180714
 */
@Mapper
@Component(value ="wxOpinionMapper")
public interface WxOpinionMapper {

    @InsertProvider(type = WxOpinionSqlProvider.class, method = "insert")
    boolean insert(WxOpinion wxOpinion);

    @UpdateProvider(type = WxOpinionSqlProvider.class, method = "update")
    boolean update(WxOpinion wxOpinion);

    @Delete("delete from t_wx_opinion where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_opinion WHERE id = #{id}")
    @Results(id = "wxOpinion", value = {
         @Result(id=true, column="id",property="id",javaType=Long.class)
             ,@Result(column="did",property="did",javaType=Long.class)
             ,@Result(column="open_id",property="openId",javaType=String.class)
             ,@Result(column="content",property="content",javaType=String.class)
             ,@Result(column="reader",property="reader",javaType=String.class)
             ,@Result(column="read_status",property="readStatus",javaType=Integer.class)
             ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
             ,@Result(column="updTime",property="updTime",javaType=Date.class)
    })
    WxOpinion findById(Integer id);

    @Select("SELECT * FROM t_wx_opinion limit 1000")
    @ResultMap("wxOpinion")
    List<WxOpinion> findListAll();

}