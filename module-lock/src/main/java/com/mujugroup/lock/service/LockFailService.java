package com.mujugroup.lock.service;

import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.lock.model.LockFail;
import com.mujugroup.lock.objeck.bo.fail.FailBo;
import com.mujugroup.lock.objeck.vo.fail.FailVo;
import com.mujugroup.lock.objeck.vo.fail.ListVo;
import com.mujugroup.lock.objeck.vo.fail.PutVo;
import com.mujugroup.lock.objeck.vo.fail.TotalVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
public interface LockFailService {
    TotalVo getFailCount(String uid) throws DataException;

    List<FailBo> getFailInfoList(Map<String, String> map, ListVo listVo) throws DataException;

    List<FailVo> toFailVo(List<FailBo> list);

    LockFail getFailInfoByDid(String did, LockFail.FailType failFlag, String errorCode);

    boolean insert(LockFail lockFail);

    boolean update(LockFail lockFail);

    void getModel(LockFail lockFail, int aid, int hid, int oid, long did, LockFail.FailType failCode, String errorCode, Date time, long bid);

    void modifyModel(LockFail lockFail, String aid, String hid, String oid, Date date);

    boolean modify(int uid, PutVo putVo) throws ParamException;
}
