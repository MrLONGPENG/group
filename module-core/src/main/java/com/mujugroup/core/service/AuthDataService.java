package com.mujugroup.core.service;

import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.objeck.bo.TreeBO;
import com.mujugroup.core.objeck.vo.TreeVO;

import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
public interface AuthDataService {

    List<TreeBO> getAgentAuthData(long id);

    List<TreeBO> getHospitalAuthData(long id);

    List<TreeBO> getDepartmentAuthData(long id);

    List<TreeBO> getAuthTreeByAid(String aid);

    List<TreeBO> getAuthTreeByHid(String hid);

    List<TreeVO> treeBoToVo(List<TreeBO> list);

    String toJsonString(List<TreeBO> list);

    int addAuthData(int uid, String[] authData);

    int deleteByUid(int uid);

    int updateAuthData(int uid, String[] authData);

    List<String> getAuthDataList(int uid);

    List<DBMap> getAuthData(int uid);

    List<TreeBO> getAllAgentList();

    Map<String, String> getAuthDataByUid(int uid);

}
