package com.mujugroup.core.service;

import com.mujugroup.core.objeck.bo.TreeBO;
import com.mujugroup.core.objeck.vo.TreeVO;

import java.util.List;

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
}
