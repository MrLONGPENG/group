package com.mujugroup.core.service;

import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBO;
import com.mujugroup.core.objeck.vo.hospital.AddVo;
import com.mujugroup.core.objeck.vo.hospital.ListVo;
import com.mujugroup.core.objeck.vo.hospital.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;

/**
 * @author leolaurel
 */
public interface HospitalService {

    List<SelectVO> getHospitalList(int uid, int aid, String name) throws DataException;

    List<SelectVO> getHospitalListByUid(String type, long uid) throws DataException;

    List<SelectVO> getAgentHospitalListByUid(String type, long uid) throws DataException;

    List<Hospital> findListByAid(String aid);

    List<Hospital> getHospitalByRegion(String pid, String cid);

    List<HospitalBO> getHospitalBoByIds(String[] array);

    String getHospitalName(int id);

    boolean add(int uid, AddVo addVo) throws ParamException, DataException;

    boolean modify(int uid, PutVo hospitalPutVo) throws ParamException, DataException;

    boolean remove(int uid, String hid) throws ParamException, DataException;

    Integer getProvinceCity(int cid, int pid);

    Integer isExitsName(String aid, String name);

    List<ListVo> findAll(int uid, int aid, String name, int provinceId, int cityId) throws ParamException, DataException;

}
