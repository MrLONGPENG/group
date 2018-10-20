package com.mujugroup.core.service;

import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBO;
import com.mujugroup.core.objeck.vo.Hospital.HospitalVo;
import com.mujugroup.core.objeck.vo.Hospital.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import ma.glasnost.orika.ObjectFactory;
import ma.glasnost.orika.metadata.Type;

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

    String getHospitalName(int id);

    boolean add(int uid, HospitalVo hospitalVo) throws ParamException;

    boolean modify(String id,PutVo hospitalPutVo)throws ParamException;

    boolean remove(String hid) throws ParamException;

    Integer getProvinceCity(int cid, int pid);

    Integer isExitsName(String aid,String name);


}
