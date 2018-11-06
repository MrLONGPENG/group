package com.mujugroup.core.service;

import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.agent.AgentVo;
import com.mujugroup.core.objeck.vo.agent.PutVo;
import com.mujugroup.core.objeck.vo.SelectVo;

import java.util.List;

/**
 * @author leolaurel
 */
public interface AgentService {
    boolean insertAgent(int uid, AgentVo agentVo) throws ParamException, DataException;

    boolean updateAgent(String uid, PutVo agentPutVo) throws ParamException, DataException;

    boolean exist(int id);

    boolean deleteById(String uid, String id) throws ParamException, DataException;

    Agent findById(Integer id);

    List<SelectVo> getAgentList();

    List<SelectVo> getTheAgentList();

    List<SelectVo> getAgentListByUid(long uid);

    List<SelectVo> getAgentHospitalByUid(long uid);

    List<Agent> findAll(String uid,String name,int enable) throws ParamException,DataException;

}
