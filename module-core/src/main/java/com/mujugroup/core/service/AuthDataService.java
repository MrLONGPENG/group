package com.mujugroup.core.service;

import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.objeck.bo.TreeBo;
import com.mujugroup.core.objeck.vo.TreeVo;

import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
public interface AuthDataService {

    List<TreeBo> getAgentAuthData(long id);

    List<TreeBo> getHospitalAuthData(long id);

    List<TreeBo> getDepartmentAuthData(long id);

    List<TreeBo> getAuthTreeByAid(String aid);

    List<TreeBo> getAuthTreeByHid(String hid);

    List<TreeVo> treeBoToVo(List<TreeBo> list);

    String toJsonString(List<TreeBo> list);

    int addAuthData(int uid, String[] authData);

    int deleteByUid(int uid);

    int updateAuthData(int uid, String[] authData);

    List<String> getAuthDataList(int uid);

    List<DBMap> getAuthData(int uid);

    List<TreeBo> getAllAgentList();

    Map<String, String> getAuthDataByUid(int uid);

}
