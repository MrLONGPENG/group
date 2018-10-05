package com.mujugroup.core.service;

import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBO;
import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;

/**
 * @author leolaurel
 */
public interface HospitalService {

    List<SelectVO> getHospitalList(int aid, String name);

    List<SelectVO> getHospitalListByUid(String type, long uid);

    List<SelectVO> getAgentHospitalListByUid(String type, long uid);

    List<Hospital> findListByAid(String aid);

    List<Hospital> getHospitalByRegion(String pid, String cid);

    List<HospitalBO> getHospitalBoByIds(String[] array);
}
