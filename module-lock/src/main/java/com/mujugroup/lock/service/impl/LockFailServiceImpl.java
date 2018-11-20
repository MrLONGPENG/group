package com.mujugroup.lock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.mujugroup.lock.mapper.LockFailMapper;
import com.mujugroup.lock.model.LockFail;
import com.mujugroup.lock.model.LockRecord;
import com.mujugroup.lock.objeck.bo.fail.FailBo;
import com.mujugroup.lock.objeck.vo.fail.FailVo;
import com.mujugroup.lock.objeck.vo.fail.ListVo;
import com.mujugroup.lock.objeck.vo.fail.PutVo;
import com.mujugroup.lock.objeck.vo.fail.TotalVo;
import com.mujugroup.lock.service.LockFailService;
import com.mujugroup.lock.service.feign.ModuleCoreService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
@Service("lockFailService")
public class LockFailServiceImpl implements LockFailService {


    private final LockFailMapper lockFailMapper;
    private final ModuleCoreService moduleCoreService;
    private final MapperFactory mapperFactory;

    @Autowired
    public LockFailServiceImpl(LockFailMapper lockFailMapper, ModuleCoreService moduleCoreService, MapperFactory mapperFactory) {
        this.lockFailMapper = lockFailMapper;
        this.moduleCoreService = moduleCoreService;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public TotalVo getFailCount(String uid) throws DataException {
        TotalVo totalVo = new TotalVo();
        Map<String, String> map = moduleCoreService.getAuthData(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        List<DBMap> list = lockFailMapper.getFailCount(map.get(CoreConfig.AUTH_DATA_AGENT)
                , map.get(CoreConfig.AUTH_DATA_HOSPITAL)
                , map.get(CoreConfig.AUTH_DATA_DEPARTMENT));
        for (DBMap dbMap : list) {
            switch (dbMap.getKey()) {
                case LockFail.FAIL_TYPE_POWER:
                    totalVo.setPowerCount(Integer.parseInt(dbMap.getValue()));
                    break;
                case LockFail.FAIL_TYPE_SIGNAL:
                    totalVo.setSignalCount(Integer.parseInt(dbMap.getValue()));
                    break;
                case LockFail.FAIL_TYPE_SWITCH:
                    totalVo.setSwitchCount(Integer.parseInt(dbMap.getValue()));
                    break;
            }
        }
        return totalVo;
    }

    @Override
    @MergeResult
    public List<FailBo> getFailInfoList(Map<String, String> map, ListVo listVo) throws DataException {
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        PageHelper.startPage(listVo.getPageNum(), listVo.getPageSize());
        return lockFailMapper.getFailInfoList(map.get(CoreConfig.AUTH_DATA_AGENT)
                , map.get(CoreConfig.AUTH_DATA_HOSPITAL), map.get(CoreConfig.AUTH_DATA_DEPARTMENT)
                , listVo.getType(), listVo.getStatus(), listVo.getDid(), listVo.getBid(), listVo.getLastRefreshStart(), listVo.getLastRefreshEnd());
    }

    public List<FailVo> toFailVo(List<FailBo> list) {
        mapperFactory.classMap(FailBo.class, FailVo.class)
                .field("oid", "department")
                .field("bed", "bed")
                .fieldMap("status").converter("statusTypeConvert").add()
                .fieldMap("electric").converter("electricConvert").add()
                .fieldMap("lastRefresh").converter("dateConvertStr").add()
                .fieldMap("endTime").converter("dateConvert").add()
                .fieldMap("resolveStatus").converter("resolveTypeConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(list, FailVo.class);
    }

    @Override
    public LockFail getFailInfoByDid(String did, LockFail.ErrorType errorType) {
        return lockFailMapper.getFailInfoByDid(did, errorType.getFailFlag(), errorType.getErrorCode());
    }

    @Override
    public boolean insert(LockFail lockFail) {
        return lockFailMapper.insert(lockFail);
    }

    @Override
    public void getModel(LockFail lockFail, LockFail.ErrorType errorType, InfoTo info, LockRecord record) {
        lockFail.setAid(Integer.valueOf(info.getAid()));
        lockFail.setHid(Integer.valueOf(info.getHid()));
        lockFail.setOid(Integer.valueOf(info.getOid()));
        lockFail.setFailCode(errorType.getFailCode());
        lockFail.setFailFlag(errorType.getFailFlag());
        lockFail.setErrorCode(errorType.getErrorCode());
        if (record == null) {
            lockFail.setDid(Long.parseLong(info.getAid()));
            lockFail.setLockId(Long.parseLong(info.getBid()));
            lockFail.setLastRefresh(new Date());
        } else {
            lockFail.setDid(record.getDid());
            lockFail.setLockId(record.getLockId());
            lockFail.setLastRefresh(record.getCrtTime());
        }

    }

    @Override
    public void modifyModel(LockFail lockFail, String aid, String hid, String oid, Date date) {
        lockFail.setAid(Integer.parseInt(aid));
        lockFail.setHid(Integer.parseInt(hid));
        lockFail.setOid(Integer.parseInt(oid));
        lockFail.setLastRefresh(date);
        lockFailMapper.update(lockFail);
    }

    @Override
    public boolean modify(int uid, PutVo putVo) throws ParamException {
        LockFail lockFail = lockFailMapper.findById((int) putVo.getId());
        if (lockFail == null) throw new ParamException("该异常不存在");
        lockFail.setStatus(putVo.getType());
        lockFail.setExplain(putVo.getExplain());
        lockFail.setFinishTime(new Date());
        lockFail.setResolveMan(putVo.getResolveMan() == 0 ? uid : putVo.getResolveMan());
        return lockFailMapper.update(lockFail);
    }

    @Override
    public boolean update(LockFail lockFail) {
        return lockFailMapper.update(lockFail);
    }


}
