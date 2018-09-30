package com.mujugroup.core.service;

import com.lveqia.cloud.common.exception.ExistException;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;

/**
 * @author leolaurel
 */
public interface AgentService {

    int insertAgent(Agent agent);

    int updateAgent(Agent agent);

    boolean exist(int id);

    int deleteById(int id) throws ExistException;

    Agent findById(Integer id);

    List<SelectVO> getAgentList();

    List<SelectVO> getTheAgentList();

    List<SelectVO> getAgentListByUid(long uid);

    List<SelectVO> getAgentHospitalByUid(long uid);
}
