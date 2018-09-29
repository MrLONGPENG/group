package com.mujugroup.core.service;

import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;

/**
 * @author leolaurel
 */
public interface AgentService {

    List<SelectVO> getAgentList();

    List<SelectVO> getTheAgentList();

    List<SelectVO> getAgentListByUid(long uid);

    List<SelectVO> getAgentHospitalByUid(long uid);
}
