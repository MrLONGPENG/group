package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.exception.ExistException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.AgentMapper;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.Agent.AgentVo;
import com.mujugroup.core.objeck.vo.Agent.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.AgentService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("agentService")
public class AgentServiceImpl implements AgentService {
    private final AgentMapper agentMapper;
    private final MapperFactory mapperFactory;

    @Autowired
    public AgentServiceImpl(AgentMapper agentMapper, MapperFactory mapperFactory) {
        this.agentMapper = agentMapper;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public boolean exist(int id) {
        return agentMapper.exist(id);
    }

    @Override
    public Agent findById(Integer id) {
        return agentMapper.findById(id);
    }

    @Override
    public boolean deleteById(String id) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("代理商编号不能为空!");
        if (!StringUtil.isNumeric(id)) throw new ParamException("代理商编号必须为数字!");
        if (agentMapper.findById(Integer.parseInt(id))==null)throw new ParamException("要删除的代理商不存在,请重新选择");
        if (exist(Integer.parseInt(id)) == true) throw new ParamException("该代理商下存在相应的医院，无法进行删除!");
        return agentMapper.deleteById(Integer.parseInt(id));
    }

    @Override
    public boolean insertAgent(AgentVo agentVo) throws ParamException {
        if (StringUtil.isEmpty(agentVo.getName())) throw new ParamException("代理商名称不能为空");
        if (agentMapper.isExistName(agentVo.getName()) > 0) throw new ParamException("该名称已存在");
        agentVo.setCrtTime(new Date());
        //设置代理商状态为启用
        agentVo.setEnable(1);
        Agent agent = agentVoToAgent(agentVo, AgentVo.class);
        return agentMapper.insert(agent);
    }

    @Override
    public boolean updateAgent(String id, PutVo agentPutVo) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("代理商编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("代理商编号必须为数字");
        if (agentMapper.findById(Integer.parseInt(id))==null)throw new ParamException("要修改的代理商不存在,请重新选择");
        if (agentMapper.isExistName(agentPutVo.getName()) > 0) throw new ParamException("该代理商名称已存在,请重新输入");
        agentPutVo.setId(Integer.parseInt(id));
        Agent agent = agentVoToAgent(agentPutVo, PutVo.class);
        return agentMapper.update(agent);
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

    private Agent getAgent(String name, int enable) {
        Agent agent = new Agent();
        agent.setName(name);
        agent.setEnable(enable);
        return agent;
    }

    @Override
    public List<SelectVO> getAgentList() {
        return agentMapper.getAgentList();
    }

    /**
     * 将VO对象转为Model
     *
     * @param obj
     * @param voType
     * @return
     */
    private Agent agentVoToAgent(Object obj, Class<?> voType) {
        mapperFactory.classMap(voType, Agent.class)
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Agent.class);
    }


}
