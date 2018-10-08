package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.exception.ExistException;
import com.mujugroup.core.mapper.AgentMapper;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("agentService")
public class AgentServiceImpl implements AgentService {
    @Override
    public boolean exist(int id) {
        return agentMapper.exist(id);
    }

    @Override
    public Agent findById(Integer id) {
        return agentMapper.findById(id);
    }

    @Override
    public int deleteById(int id) throws ExistException {
        if (exist(id) == true) throw new ExistException("该代理商下存在相应的医院，无法进行删除!");
        return agentMapper.deleteById(id) == true ? 1 : 0;
    }

    @Override
    public int updateAgent(int id,String name,int enable) {
        Agent agent=agentMapper.findById(id);
        agent.setName(name);
        agent.setEnable(enable);
        return agentMapper.update(agent) == true ? 1 : 0;
    }

    private final AgentMapper agentMapper;

    @Autowired
    public AgentServiceImpl(AgentMapper agentMapper) {
        this.agentMapper = agentMapper;
    }


    @Override
    public List<SelectVO> getAgentListByUid(long uid) {
        return agentMapper.getAgentListByUid(uid);
    }

    @Override
    public List<SelectVO> getAgentHospitalByUid(long uid) {
        return agentMapper.getAgentHospitalByUid(uid);
    }

    @Override
    public List<SelectVO> getTheAgentList() {
        return agentMapper.getTheAgentList();
    }

    @Override
    public int insertAgent(String name, int enable) {
        return agentMapper.insert(getAgent(name,enable)) == true ? 1 : 0;
    }

    private Agent getAgent(String name, int enable) {
        Agent agent = new Agent();
        agent.setName(name);
        agent.setEnable(enable);
        return  agent;
    }


    @Override
    public List<SelectVO> getAgentList() {
        return agentMapper.getAgentList();
    }


}
