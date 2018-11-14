package com.mujugroup.lock.sql;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.model.LockFail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 锁故障表,SQL语句组装类
 * 类名:LockFailSqlProvider
 *
 * @author leolaurel
 * 创建时间:20181107
 */
public class LockFailSqlProvider {

    public String insert(LockFail lockFail) {
        return new SQL() {{
            INSERT_INTO("t_lock_fail");
            if (lockFail.getId() != null) VALUES("`id`", "#{id}");
            if (lockFail.getDid() != null) VALUES("`did`", "#{did}");
            if (lockFail.getLockId() != null) VALUES("`lock_id`", "#{lockId}");
            if (lockFail.getAid() != null) VALUES("`aid`", "#{aid}");
            if (lockFail.getHid() != null) VALUES("`hid`", "#{hid}");
            if (lockFail.getOid() != null) VALUES("`oid`", "#{oid}");
            if (lockFail.getFailCode() != null) VALUES("`fail_code`", "#{failCode}");
            if (lockFail.getErrorCode() != null) VALUES("`error_code`", "#{errorCode}");
            if (lockFail.getLastRefresh() != null) VALUES("`last_refresh`", "#{lastRefresh}");
            if (lockFail.getFinishTime() != null) VALUES("`finish_time`", "#{finishTime}");
            if (lockFail.getStatus() != null) VALUES("`status`", "#{status}");
            if (lockFail.getResolveMan() != null) VALUES("`resolve_man`", "#{resolveMan}");
            if (lockFail.getResolveCode() != null) VALUES("`resolve_code`", "#{resolveCode}");
            if (lockFail.getExplain() != null) VALUES("`explain`", "#{explain}");
        }}.toString();
    }


    public String update(LockFail lockFail) {
        return new SQL() {{
            UPDATE("t_lock_fail");
            if (lockFail.getId() != null) SET("`id` = #{id}");
            if (lockFail.getDid() != null) SET("`did` = #{did}");
            if (lockFail.getLockId() != null) SET("`lock_id` = #{lockId}");
            if (lockFail.getAid() != null) SET("`aid` = #{aid}");
            if (lockFail.getHid() != null) SET("`hid` = #{hid}");
            if (lockFail.getOid() != null) SET("`oid` = #{oid}");
            if (lockFail.getFailCode() != null) SET("`fail_code` = #{failCode}");
            if (lockFail.getErrorCode() != null) SET("`error_code` = #{errorCode}");
            if (lockFail.getLastRefresh() != null) SET("`last_refresh` = #{lastRefresh}");
            if (lockFail.getFinishTime() != null) SET("`finish_time` = #{finishTime}");
            if (lockFail.getStatus() != null) SET("`status` = #{status}");
            if (lockFail.getResolveMan() != null) SET("`resolve_man` = #{resolveMan}");
            if (lockFail.getResolveCode() != null) SET("`resolve_code` = #{resolveCode}");
            if (lockFail.getExplain() != null) SET("`explain` = #{explain}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getFailCount(@Param(value = "aid") String aid, @Param(value = "hid") String hid
            , @Param(value = "oid") String oid) {
        return new SQL() {{
            SELECT("fail_code AS `key`, COUNT(*) AS `value`");
            FROM("t_lock_fail");
            WHERE("`status` != 3");
            StringBuilder sql = new StringBuilder();
            if (!StringUtil.isEmpty(aid) && aid.contains(Constant.SIGN_DOU_HAO)) {
                sql.append("aid in (").append(aid).append(")");
            } else if (!StringUtil.isEmpty(aid)) {
                sql.append("aid = #{aid} ");
            }
            if (!StringUtil.isEmpty(hid) && hid.contains(Constant.SIGN_DOU_HAO)) {
                if (sql.length() > 0) sql.append(" OR ");
                sql.append("hid in (").append(hid).append(")");
            } else if (!StringUtil.isEmpty(hid)) {
                if (sql.length() > 0) sql.append(" OR ");
                sql.append("hid = #{hid} ");
            }
            if (!StringUtil.isEmpty(oid) && oid.contains(Constant.SIGN_DOU_HAO)) {
                if (sql.length() > 0) sql.append(" OR ");
                sql.append("oid in (").append(oid).append(")");
            } else if (!StringUtil.isEmpty(oid)) {
                if (sql.length() > 0) sql.append(" OR ");
                sql.append("oid = #{oid} ");
            }
            if (sql.length() > 0) AND().WHERE(sql.toString());
            GROUP_BY("fail_code");
        }}.toString();

    }

    public String getFailInfoList(@Param(value = "aid") String aid,@Param(value = "hid") String hid,@Param(value = "oid") String oid,@Param(value = "failCode")String failCode) {
        return new SQL() {{
            SELECT("f.did,d.dict_name,i.lock_status,i.battery_stat,i.electric, i.last_refresh" +
                    ", f.oid, f.did as bed, f.did as endTime");
            FROM("t_lock_fail f,t_lock_dict d,t_lock_info i");
            WHERE("f.error_code=d.dict_code AND d.dict_type='Fail_Error' " +
                    "AND f.lock_id= i.lock_id AND f.`status` != 3" );
            if (!StringUtil.isEmpty(failCode)){
                AND().WHERE("f.fail_code= #{failCode}");
            }
            StringBuilder sql = new StringBuilder();
            if (!StringUtil.isEmpty(aid) && aid.contains(Constant.SIGN_DOU_HAO)) {
                sql.append("f.aid in (").append(aid).append(")");
            } else if (!StringUtil.isEmpty(aid)) {
                sql.append("f.aid = #{aid} ");
            }
            if (!StringUtil.isEmpty(hid) && hid.contains(Constant.SIGN_DOU_HAO)) {
                if (sql.length() > 0) sql.append(" OR ");
                sql.append("f.hid in (").append(hid).append(")");
            } else if (!StringUtil.isEmpty(hid)) {
                if (sql.length() > 0) sql.append(" OR ");
                sql.append("f.hid = #{hid} ");
            }
            if (!StringUtil.isEmpty(oid) && oid.contains(Constant.SIGN_DOU_HAO)) {
                if (sql.length() > 0) sql.append(" OR ");
                sql.append("f.oid in (").append(oid).append(")");
            } else if (!StringUtil.isEmpty(oid)) {
                if (sql.length() > 0) sql.append(" OR ");
                sql.append("f.oid = #{oid} ");
            }
            AND().WHERE(sql.toString());
        }}.toString();

    }
}
