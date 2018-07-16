package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxUser;
import com.mujugroup.wx.sql.WxUserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 微信用户基础信息表,数据库操作接口类
 * 类名:WxUserMapper
 * 创建人:leolaurel
 * 创建时间:20180626
 */
@Mapper
@Component(value ="wxUserMapper")
public interface WxUserMapper {

    @InsertProvider(type = WxUserSqlProvider.class, method = "insert")
    boolean insert(WxUser wxUser);

    @UpdateProvider(type = WxUserSqlProvider.class, method = "update")
    boolean update(WxUser wxUser);

    @Delete("delete from t_wx_user where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_user WHERE id = #{id}")
    @Results(id = "wxUser", value = {
         @Result(id=true, column="id",property="id",javaType=Long.class)
            ,@Result(column="phone",property="phone",javaType=String.class)
            ,@Result(column="open_id",property="openId",javaType=String.class)
            ,@Result(column="union_id",property="unionId",javaType=String.class)
            ,@Result(column="nick_name",property="nickName",javaType=String.class)
            ,@Result(column="gender",property="gender",javaType=Integer.class)
            ,@Result(column="language",property="language",javaType=String.class)
            ,@Result(column="country",property="country",javaType=String.class)
            ,@Result(column="province",property="province",javaType=String.class)
            ,@Result(column="city",property="city",javaType=String.class)
            ,@Result(column="avatar_url",property="avatarUrl",javaType=String.class)
            ,@Result(column="session_key",property="sessionKey",javaType=String.class)
            ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
            ,@Result(column="update_time",property="updateTime",javaType=Date.class)
    })
    WxUser findById(Integer id);

    @Select("SELECT * FROM t_wx_user limit 1000")
    @ResultMap("wxUser")
    List<WxUser> findListAll();

    @Select("SELECT * FROM t_wx_user WHERE open_id = #{openid}")
    @ResultMap("wxUser")
    WxUser findByOpenId(String openid);
}