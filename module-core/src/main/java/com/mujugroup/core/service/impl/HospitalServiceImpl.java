package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBO;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {


    private final HospitalMapper hospitalMapper;

    @Override
    public List<SelectVO> getAgentHospitalListByUid(String type, long uid) {
        return hospitalMapper.getAgentHospitalListByUid(type, uid);
    }

    @Override
    public List<Hospital> findListByAid(String aid) {
        return hospitalMapper.findListByAid(aid);
    }

    @Override
    public List<Hospital> getHospitalByRegion(String pid, String cid) {
        return hospitalMapper.getHospitalByRegion(pid, cid);
    }

    @Override
    public List<HospitalBO> getHospitalBoByIds(String[] array) {
        return hospitalMapper.getHospitalBoByIds(StringUtil.toLinkByDouHao((Object[]) array));
    }

    @Override
    public String getHospitalName(int id) {
        return hospitalMapper.getHospitalName(id);
    }

    @Override
    public List<SelectVO> getHospitalListByUid(String type, long uid) {
        return hospitalMapper.getHospitalListByUid(type, uid);
    }

    @Autowired
    public HospitalServiceImpl(HospitalMapper hospitalMapper) {
        this.hospitalMapper = hospitalMapper;
    }


    @Override
    public List<SelectVO> getHospitalList(int aid, String name) {
        return hospitalMapper.getHospitalList(aid, name);
    }
}
