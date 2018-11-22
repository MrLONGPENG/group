package com.mujugroup.data.service.feign.error;

import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.SelectTo;
import com.mujugroup.data.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ModuleCoreServiceError implements ModuleCoreService {
    private Map<String, String> EMPTY_MAP = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(ModuleCoreServiceError.class);


    @Override
    public Map<String, String> getNewlyActiveCount(String param) {
        logger.warn("data->remote core fail, method:getNewlyActiveCount param:{}", param);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getTotalActiveCount(String param) {
        logger.warn("data->remote core fail, method:getTotalActiveCount param:{}", param);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getAgentById(String param) {
        logger.warn("data->remote core fail, method:getAgentById param:{}", param);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getHospitalById(String param) {
        logger.warn("data->remote core fail, method:getHospitalById param:{}", param);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getProvinceByHid(String param) {
        logger.warn("data->remote core fail, method:getProvinceByHid param:{}", param);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getCityByHid(String param) {
        logger.warn("data->remote core fail, method:getCityByHid param:{}", param);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getDepartmentById(String param) {
        logger.warn("data->remote core fail, method:getDepartmentById param:{}", param);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getBedInfoByDid(String param) {
        logger.warn("data->remote core fail, method:getBedInfoByDid param:{}", param);
        return EMPTY_MAP;
    }

    @Override
    public Map<Integer, String> getHospitalByAid(int aid) {
        return new HashMap<>();
    }

    @Override
    public Set<Integer> getHospitalByRegion(int pid, int cid) {
        return new HashSet<>();
    }

    @Override
    public Map<String, String> getAuthData(String uid) {
        logger.warn("data->remote core fail, method:getAuthData param:{}", uid);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getHospitalJson(String hid) {
        logger.warn("data->remote core fail, method:getHospitalJson param:{}", hid);
        return EMPTY_MAP;
    }

    @Override
    public PageInfo<SelectTo> getOidByOid(String oid, int pageNum, int pageSize) {
        logger.warn("data->remote core fail, method:getOidByOid {},{},{}", oid, pageNum, pageSize);
        return null;
    }

    @Override
    public PageInfo<InfoTo> getDeviceInfoListByOid(String oid, int pageNum, int pageSize) {
        logger.warn("data->remote core fail, method:getDevcieInfoListByOid {},{},{}", oid, pageNum, pageSize);
        return null;
    }

    @Override
    public PageInfo<SelectTo> getAuthLevel(int uid, String level, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public PageInfo<SelectTo> getOidByHid(String hid, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public InfoTo getDeviceInfo(String did, String bid) {
        logger.warn("data->remote core fail, method:getDeviceInfo param:{}", did, bid);
        return null;
    }
}
