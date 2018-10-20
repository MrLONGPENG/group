package com.mujugroup.core.service;

import com.lveqia.cloud.common.exception.ExistException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.Agent.AgentVo;
import com.mujugroup.core.objeck.vo.Agent.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;

/**
 * @author leolaurel
 */
public interface AgentService {
    boolean insertAgent(AgentVo agentVo) throws ParamException;

    boolean updateAgent(String id, PutVo agentPutVo) throws ParamException;

    boolean exist(int id);

    boolean deleteById(String id) throws ParamException;

    Agent findById(Integer id);

    List<SelectVO> getAgentList();

    List<SelectVO> getTheAgentList();

    List<SelectVO> getAgentListByUid(long uid);

    List<SelectVO> getAgentHospitalByUid(long uid);


}
