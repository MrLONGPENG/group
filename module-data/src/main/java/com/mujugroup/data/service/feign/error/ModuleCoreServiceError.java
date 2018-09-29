package com.mujugroup.data.service.feign.error;

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

    private final Logger logger = LoggerFactory.getLogger(ModuleCoreServiceError.class);


    @Override
    public Map<String, String> getNewlyActiveCount(String param) {
        logger.debug("data->remote core fail, method:getNewlyActiveCount param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getTotalActiveCount(String param) {
        logger.debug("data->remote core fail, method:getTotalActiveCount param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getAgentById(String param) {
        logger.debug("data->remote core fail, method:getAgentById param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getHospitalById(String param) {
        logger.debug("data->remote core fail, method:getHospitalById param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getProvinceByHid(String param) {
        logger.debug("data->remote core fail, method:getProvinceByHid param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getCityByHid(String param) {
        logger.debug("data->remote core fail, method:getCityByHid param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getDepartmentById(String param) {
        logger.debug("data->remote core fail, method:getDepartmentById param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getBedInfoByDid(String param) {
        logger.debug("data->remote core fail, method:getBedInfoByDid param:{}",param);
        return new HashMap<>();
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
    public Map<String, String> getHospitalJson(String hid) {
        logger.debug("data->remote core fail, method:getHospitalJson param:{}",hid);
        return null;
    }
}
