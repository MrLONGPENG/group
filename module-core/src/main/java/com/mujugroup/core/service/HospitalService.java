package com.mujugroup.core.service;

import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;

/**
 * @author leolaurel
 */
public interface HospitalService {

    List<SelectVO> getHospitalList(int aid, String name);

    List<SelectVO> getHospitalListByUid(int type, long uid);

    List<SelectVO> getAgentHospitalListByUid(int type, long uid);
}
