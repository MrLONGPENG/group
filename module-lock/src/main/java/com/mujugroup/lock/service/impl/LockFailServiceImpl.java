package com.mujugroup.lock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.mapper.LockFailMapper;
import com.mujugroup.lock.model.LockFail;
import com.mujugroup.lock.objeck.bo.fail.FailBo;
import com.mujugroup.lock.objeck.vo.fail.FailVo;
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
    public List<FailBo> getFailInfoList(Map<String, String> map, int pageNum, int pageSize, int type) throws DataException {
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        PageHelper.startPage(pageNum, pageSize);

        return lockFailMapper.getFailInfoList(map.get(CoreConfig.AUTH_DATA_AGENT)
                , map.get(CoreConfig.AUTH_DATA_HOSPITAL)
                , map.get(CoreConfig.AUTH_DATA_DEPARTMENT), type == 1 ? LockFail.FAIL_TYPE_POWER : type == 2 ? LockFail.FAIL_TYPE_SIGNAL : type == 4 ? LockFail.FAIL_TYPE_SWITCH : null);
    }

    public List<FailVo> toFailVo(List<FailBo> list) {
        mapperFactory.classMap(FailBo.class, FailVo.class)
                .field("oid", "department")
                .field("bed", "bed")
                .fieldMap("battery").converter("getPercentConvert").add()
                .fieldMap("status").converter("statusTypeConvert").add()
                .fieldMap("electric").converter("electricConvert").add()
                .fieldMap("lastRefresh").converter("dateConvertStr").add()
                .fieldMap("endTime").converter("dateConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(list, FailVo.class);
    }

    @Override
    public LockFail getFailInfoByDid(String did, String failCode, String errorCode) {
        return lockFailMapper.getFailInfoByDid(did, failCode, errorCode);
    }

    @Override
    public boolean insert(LockFail lockFail) {
        return lockFailMapper.insert(lockFail);
    }

    @Override
    public void getModel(LockFail lockFail, int aid, int hid, int oid, long did, String failCode, String errorCode, Date time, long bid) {
        lockFail.setAid(aid);
        lockFail.setHid(hid);
        lockFail.setOid(oid);
        lockFail.setLockId(bid);
        lockFail.setDid(did);
        lockFail.setFailCode(failCode);
        lockFail.setErrorCode(errorCode);
        lockFail.setLastRefresh(time);
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
    public boolean modify(int uid, int type, String did, String failCode, String errorCode, String explain) throws ParamException {
        if (StringUtil.isEmpty(did) || StringUtil.isEmpty(failCode) || StringUtil.isEmpty(errorCode))
            throw new ParamException("请输入did,故障编码,错误编码");
        if (type==3||type==4){
            LockFail lockFail = lockFailMapper.getFailInfoByDid(did, failCode, errorCode);
            if (lockFail == null) throw new ParamException("该异常不存在");
            lockFail.setStatus(type);
            lockFail.setExplain(explain);
            lockFail.setResolveMan(uid);
            lockFail.setFinishTime(new Date());
            return lockFailMapper.update(lockFail);
        }
        throw new ParamException("目前仅支持已解决和未解决两种状态");
    }

    @Override
    public boolean update(LockFail lockFail) {
        return lockFailMapper.update(lockFail);
    }


}
