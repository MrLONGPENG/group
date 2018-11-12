package com.mujugroup.lock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.objeck.DBMap;
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
        List<DBMap> list= lockFailMapper.getFailCount(map.get(CoreConfig.AUTH_DATA_AGENT)
                , map.get(CoreConfig.AUTH_DATA_HOSPITAL)
                , map.get(CoreConfig.AUTH_DATA_DEPARTMENT));
        for (DBMap dbMap:list){
            switch (dbMap.getKey()){
                case LockFail.FAIL_TYPE_POWER:
                    totalVo.setPowerCount(Integer.parseInt(dbMap.getValue())); break;
                case LockFail.FAIL_TYPE_SIGNAL:
                    totalVo.setSignalCount(Integer.parseInt(dbMap.getValue())); break;
                case LockFail.FAIL_TYPE_SWITCH:
                    totalVo.setSwitchCount(Integer.parseInt(dbMap.getValue())); break;
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
                , map.get(CoreConfig.AUTH_DATA_DEPARTMENT), type);
    }

    public List<FailVo> toFailVo(List<FailBo> list) {
        mapperFactory.classMap(FailBo.class, FailVo.class)
                .field("oid", "department")
                .field("bed", "bed")
                .field("endTime", "endTime")
                .fieldMap("battery").converter("getPercentConvert").add()
                .fieldMap("status").converter("statusTypeConvert").add()
                .fieldMap("electric").converter("electricConvert").add()
                .fieldMap("lastRefresh").converter("dateConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(list, FailVo.class);
    }


}
