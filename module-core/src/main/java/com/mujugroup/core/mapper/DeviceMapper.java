package com.mujugroup.core.mapper;

import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.objeck.bean.StatusAidBean;
import com.mujugroup.core.objeck.bean.StatusHidBean;
import com.mujugroup.core.objeck.bean.StatusOidBean;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.sql.DeviceSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 设备关联表,数据库操作接口类
 * 类名:DeviceMapper
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
@Mapper
@Component(value = "deviceMapper")
public interface DeviceMapper {

    @InsertProvider(type = DeviceSqlProvider.class, method = "insert")
    boolean insert(Device device);

    @UpdateProvider(type = DeviceSqlProvider.class, method = "update")
    boolean update(Device device);

    @Delete("delete from t_device where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_device WHERE id = #{id}")
    @Results(id = "device", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "mac", property = "mac", javaType = String.class)
            , @Result(column = "agentId", property = "agentId", javaType = Integer.class)
            , @Result(column = "hospitalId", property = "hospitalId", javaType = Integer.class)
            , @Result(column = "hospitalBed", property = "hospitalBed", javaType = String.class)
            , @Result(column = "crtTime", property = "crtTime", javaType = Date.class)
            , @Result(column = "crtId", property = "crtId", javaType = Integer.class)
            , @Result(column = "status", property = "status", javaType = Integer.class)
            , @Result(column = "useflag", property = "useflag", javaType = Integer.class)
            , @Result(column = "reserve_date", property = "reserveDate", javaType = Date.class)
            , @Result(column = "imgUrl", property = "imgUrl", javaType = String.class)
            , @Result(column = "remark", property = "remark", javaType = String.class)
            , @Result(column = "depart", property = "depart", javaType = Integer.class)
            , @Result(column = "code", property = "code", javaType = String.class)
            , @Result(column = "pay", property = "pay", javaType = Integer.class)
            , @Result(column = "run", property = "run", javaType = Integer.class)
            , @Result(column = "station_id", property = "stationId", javaType = Integer.class)
            , @Result(column = "is_station", property = "isStation", javaType = Integer.class)
            , @Result(column = "update_id", property = "updateId", javaType = Integer.class)
            , @Result(column = "update_time", property = "updateTime", javaType = Date.class)
            , @Result(column = "issync", property = "issync", javaType = Integer.class)
    })
    Device findById(Integer id);


    @ResultMap("device")
    @Select("SELECT * FROM t_device")
    List<Device> findListAll();


    @ResultMap("device")
    @Select("SELECT * FROM t_device WHERE status = #{status}")
    List<Device> findListByStatus(int status);


    @Select("SELECT hospitalId as hid, agentId as aid, COUNT(DISTINCT mac) as act" +
            " FROM t_device WHERE agentId = #{aid} AND status = 14 GROUP BY hospitalId")
    @Results(value = {@Result(column = "hid", property = "hid", javaType = Integer.class)
            , @Result(column = "aid", property = "aid", javaType = Integer.class)
            , @Result(column = "act", property = "actCount", javaType = Integer.class)
            , @Result(column = "hid", property = "hospital", javaType = String.class)
            , @Result(column = "hid", property = "payCount", javaType = String.class)
    })
    List<StatusAidBean> findGroupByAid(@Param("aid") int aid);


    @Select("SELECT depart as oid, hospitalId as hid, agentId as aid, COUNT(DISTINCT mac) as act" +
            " FROM t_device WHERE agentId = #{aid} AND hospitalId = #{hid} AND status = 14 GROUP BY depart")
    @Results(value = {@Result(column = "oid", property = "oid", javaType = Integer.class)
            , @Result(column = "hid", property = "hid", javaType = Integer.class)
            , @Result(column = "aid", property = "aid", javaType = Integer.class)
            , @Result(column = "act", property = "actCount", javaType = Integer.class)
            , @Result(column = "oid", property = "department", javaType = String.class)
            , @Result(column = "oid", property = "payCount", javaType = String.class)
    })
    List<StatusHidBean> findGroupByHid(@Param("aid") int aid, @Param("hid") int hid);


    @Select("SELECT mac as did, `code` as bid, depart as oid, hospitalId as hid, agentId as aid, hospitalBed" +
            ", COUNT(DISTINCT mac) as act FROM t_device WHERE agentId = #{aid} AND hospitalId = #{hid} " +
            " AND depart = #{oid} AND status = 14 GROUP BY mac")
    @Results(value = {@Result(column = "did", property = "did", javaType = String.class)
            , @Result(column = "bid", property = "bid", javaType = String.class)
            , @Result(column = "oid", property = "oid", javaType = Integer.class)
            , @Result(column = "hid", property = "hid", javaType = Integer.class)
            , @Result(column = "aid", property = "aid", javaType = Integer.class)
            , @Result(column = "did", property = "paymentInfo", javaType = String.class)
            , @Result(column = "bid", property = "hardwareInfo", javaType = String.class)
            , @Result(column = "hospitalBed", property = "hospitalBed", javaType = String.class)
    })
    List<StatusOidBean> findGroupByOid(@Param("aid") int aid, @Param("hid") int hid, @Param("oid") int oid);


    @Results(id = "dbMap", value = {@Result(column = "key", property = "key", javaType = String.class)
            , @Result(column = "value", property = "value", javaType = String.class)})
    @SelectProvider(type = DeviceSqlProvider.class, method = "getActiveByGroup")
    List<DBMap> getActiveByGroup(@Param("aid") String aid, @Param("hid") String hid, @Param("oid") String oid
            , @Param("grain") String grain, @Param("start") String start, @Param("end") String end);


    @SelectProvider(type = DeviceSqlProvider.class, method = "getActiveCount")
    String getActiveCount(@Param("aid") String aid, @Param("hid") String hid, @Param("oid") String oid
            , @Param("start") String start, @Param("end") String end);

    @ResultType(String.class)
    @Select("SELECT `hospitalBed` FROM `t_device` WHERE `mac` = #{did}")
    String getBedInfoByDid(@Param("did") String did);

    @ResultType(Integer.class)
    @Select("SELECT COUNT(*) FROM `t_device` WHERE `mac` = #{did}")
    Integer isExistMac(@Param("did") String did);

    @ResultType(Integer.class)
    @Select("SELECT COUNT(*) FROM `t_device` WHERE `code` = #{code}")
    Integer isExistCode(@Param("code") String code);

    @SelectProvider(type = DeviceSqlProvider.class, method = "getDeviceList")
    @ResultMap("device")
    List<Device> getDeviceList(@Param(value = "did") String did, @Param(value = "bid") String bid
            , @Param(value = "bed") String bed, @Param(value = "aid") String aid, @Param(value = "hid") String hid
            , @Param(value = "oid") String oid);
}