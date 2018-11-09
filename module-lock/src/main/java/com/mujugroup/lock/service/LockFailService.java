package com.mujugroup.lock.service;

import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.objeck.to.PageTo;
import com.mujugroup.lock.objeck.bo.fail.FailBo;
import com.mujugroup.lock.objeck.vo.fail.FailVo;
import com.mujugroup.lock.objeck.vo.fail.TotalVo;

import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
public interface LockFailService {
    List<TotalVo> getFailCount(String uid) throws DataException;

    List<FailBo> getFailInfoList(Map<String, String> map,int pageNum, int pageSize, int type) throws DataException;
    List<FailVo> toFailVo(List<FailBo> list);

}
