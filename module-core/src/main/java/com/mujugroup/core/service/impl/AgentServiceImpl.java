package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.AgentMapper;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.agent.AgentVo;
import com.mujugroup.core.objeck.vo.agent.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.AgentService;
import com.mujugroup.core.service.AuthDataService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
@Service("agentService")
public class AgentServiceImpl implements AgentService {
    private final AgentMapper agentMapper;
    private final MapperFactory mapperFactory;
    private final AuthDataService authDataService;

    @Autowired
    public AgentServiceImpl(AgentMapper agentMapper, MapperFactory mapperFactory, AuthDataService authDataService) {
        this.agentMapper = agentMapper;
        this.mapperFactory = mapperFactory;
        this.authDataService = authDataService;
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
    public boolean deleteById(String uid, String id) throws ParamException, DataException {
        if (StringUtil.isEmpty(uid)) throw new ParamException("用户编号不能为空");
        if (!StringUtil.isNumeric(uid)) throw new ParamException("用户编号必须为数字");
        Map<String, String> map = authDataService.getAuthDataByUid(Integer.parseInt(uid));
        if (map.size() == 0) throw new DataException("当前用户无数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL)) throw new DataException("当前用户无最高数据权限,暂无法进行代理商删除操作");
        if (StringUtil.isEmpty(id)) throw new ParamException("代理商编号不能为空!");
        if (!StringUtil.isNumeric(id)) throw new ParamException("代理商编号必须为数字!");
        Agent agent = agentMapper.findById(Integer.parseInt(id));
        if (agent == null) throw new ParamException("要删除的代理商不存在,请重新选择");
        if (exist(Integer.parseInt(id)) == true) throw new ParamException("该代理商下存在相应的医院，无法进行删除!");
        //将当前代理商状态设置为删除状态
        agent.setEnable(Agent.TYPE_DELETE);
        return agentMapper.update(agent);
    }

    @Override
    public boolean insertAgent(int uid, AgentVo agentVo) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户无数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL)) throw new DataException("当前用户无最高数据权限,暂无法进行代理商添加");
        if (StringUtil.isEmpty(agentVo.getName())) throw new ParamException("代理商名称不能为空");
        if (agentMapper.isExistName(agentVo.getName()) > 0) throw new ParamException("该名称已存在");
        agentVo.setCrtTime(new Date());
        //设置代理商状态为启用
        agentVo.setEnable(1);
        Agent agent = agentVoToAgent(agentVo, AgentVo.class);
        boolean result = agentMapper.insert(agent);
        return result;
    }

    @Override
    public boolean updateAgent(String uid, PutVo agentPutVo) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(Integer.parseInt(uid));
        if (map.size() == 0) throw new DataException("当前用户无数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL)) throw new DataException("当前用户无最高数据权限,暂无法进行代理商编辑操作");
        Agent model=agentMapper.findById(agentPutVo.getId());
        if (model == null) throw new ParamException("要修改的代理商不存在,请重新选择");
        if (model.getName()!=null&&agentPutVo.getName()!=null){
            if (!model.getName().equals(agentPutVo.getName())){
                if (agentMapper.isExistName(agentPutVo.getName()) > 0) throw new ParamException("该代理商名称已存在,请重新输入");
            }
        }
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
    public List<Agent> findAll(String uid, String name,int enable) throws ParamException, DataException {
        if (StringUtil.isEmpty(uid)) throw new ParamException("用户编号不能为空");
        if (!StringUtil.isNumeric(uid)) throw new ParamException("用户编号必须为数字");
        Map<String, String> map = authDataService.getAuthDataByUid(Integer.parseInt(uid));
        if (map.size() == 0) throw new DataException("当前用户无数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL)) throw new DataException("当前用户无最高数据权限,暂无法进行查看");
        return agentMapper.findAll(name,enable);
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
