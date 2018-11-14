package com.mujugroup.lock.service;

import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.objeck.to.PageTo;
import com.mujugroup.lock.model.LockFail;
import com.mujugroup.lock.objeck.bo.fail.FailBo;
import com.mujugroup.lock.objeck.vo.fail.FailVo;
import com.mujugroup.lock.objeck.vo.fail.TotalVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
public interface LockFailService {
    TotalVo getFailCount(String uid) throws DataException;

    List<FailBo> getFailInfoList(Map<String, String> map, int pageNum, int pageSize, int type) throws DataException;

    List<FailVo> toFailVo(List<FailBo> list);

    LockFail getFailInfoByDid(String did, String failCode, String errorCode);

    boolean insert(LockFail lockFail);

    boolean update(LockFail lockFail);

    void getModel(LockFail lockFail, int aid, int hid, int oid, long did, String failCode, String errorCode, Date time, long bid);

    void modifyModel(LockFail lockFail, String aid, String hid, String oid, Date date);
}
