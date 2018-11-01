package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.DictDepartmentMapper;
import com.mujugroup.core.model.DictDepartment;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.objeck.vo.dictDepartment.AddVo;
import com.mujugroup.core.objeck.vo.dictDepartment.ListVo;
import com.mujugroup.core.objeck.vo.dictDepartment.PutVo;
import com.mujugroup.core.service.DictDepartmentService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("dictDepartmentService")
public class DictDepartmentServiceImpl implements DictDepartmentService {


    private final DictDepartmentMapper dictDepartmentMapper;
    private final MapperFactory mapperFactory;

    @Autowired
    public DictDepartmentServiceImpl(DictDepartmentMapper dictDepartmentMapper, MapperFactory mapperFactory) {
        this.dictDepartmentMapper = dictDepartmentMapper;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public boolean add(int uid, AddVo dictDepartmentAddVo) throws ParamException {
        if (dictDepartmentMapper.isExistName(dictDepartmentAddVo.getName()) > 0)
            throw new ParamException("该名称已存在,请重新输入");
        dictDepartmentAddVo.setCreateDate(new Date());
        dictDepartmentAddVo.setEnable(DictDepartment.TYPE_ENABLE);
        dictDepartmentAddVo.setCreateUserid(uid);
        return dictDepartmentMapper.insert(dictDepartmentVoToDictDepartment(dictDepartmentAddVo, AddVo.class));
    }

    @Override
    public boolean update(int uid, PutVo dictDepartmentPutVo) throws ParamException {
        DictDepartment dictDepartment = dictDepartmentMapper.findById(dictDepartmentPutVo.getId());
        if (dictDepartment == null) throw new ParamException("要修改的木巨科室不存在,请重新选择");
        if (!StringUtil.isEmpty(dictDepartmentPutVo.getName())){
            if (!dictDepartmentPutVo.getName().equals(dictDepartment.getName())){
                if (dictDepartmentMapper.isExistName(dictDepartmentPutVo.getName()) > 0)
                    throw new ParamException("该名称已存在,请重新输入");
            }
        }
        dictDepartmentPutVo.setUpdateTime(new Date());
        dictDepartmentPutVo.setUpdateUserid(uid);
        return dictDepartmentMapper.update(dictDepartmentVoToDictDepartment(dictDepartmentPutVo, PutVo.class));
    }

    @Override
    public boolean delete(String id) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("木巨科室编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("木巨科室编号必须为数字");
        DictDepartment dictDepartment = dictDepartmentMapper.findById(Integer.parseInt(id));
        if (dictDepartment == null) throw new ParamException("要删除的木巨科室不存在");
        return dictDepartmentMapper.deleteById(Integer.parseInt(id));
    }

    @Override
    public List<SelectVO> getDictDepartmentList(String name) {
        return dictDepartmentMapper.getDictDepartmentList(name);
    }

    @Override
    public List<ListVo> findAll(String name) {
        return dictDepartmentMapper.findAll(name);
    }

    /**
     * 将VO对象转为Model
     *
     * @param obj
     * @param voType
     * @return
     */
    private DictDepartment dictDepartmentVoToDictDepartment(Object obj, Class<?> voType) {
        mapperFactory.classMap(voType, DictDepartment.class)
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, DictDepartment.class);
    }
}
