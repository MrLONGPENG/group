package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.objeck.vo.Department.DepartmentVo;
import com.mujugroup.core.objeck.vo.Department.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.DepartmentService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentMapper departmentMapper;
    private final MapperFactory mapperFactory;

    @Override
    public boolean delete(String id) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("科室编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("科室编号必须为数字");
        if (departmentMapper.findById(Integer.parseInt(id)) == null) throw new ParamException("该科室不存在");
        return departmentMapper.deleteById(Integer.parseInt(id));
    }

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper, MapperFactory mapperFactory) {
        this.departmentMapper = departmentMapper;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public List<DBMap> findOidByHid(String hid) {
        return departmentMapper.findOidByHid(hid);
    }

    @Override
    public List<SelectVO> getDepartmentList(String name) {
        return departmentMapper.getDepartmentList(name);
    }

    @Override
    public List<SelectVO> getListByHid(String hid) {
        return departmentMapper.getListByHid(hid);
    }

    @Override
    public List<SelectVO> getListByHidOrName(String hid, String name) {
        return departmentMapper.getListByHidOrName(hid,name);
    }

    @Override
    public boolean insert(DepartmentVo departmentVo) throws ParamException {
        if (departmentVo.getHospitalId()==null)throw new ParamException("请选择所属医院");
        if (StringUtil.isEmpty(departmentVo.getName())) throw new ParamException("科室名称不能为空");
        if (departmentMapper.isExistName(departmentVo.getHospitalId().toString(),departmentVo.getName())>0)throw new ParamException("该科室已存在");
        departmentVo.setCreateDate(new Date());
        departmentVo.setStatus(1);
        return departmentMapper.insert(departmentVoToDepartment(departmentVo, DepartmentVo.class));
    }

    @Override
    public boolean update(String id, PutVo departmentPutVo) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("科室编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("科室编号必须为数字");
        if (departmentMapper.findById(Integer.parseInt(id)) == null) throw new ParamException("该科室不存在");
        if (StringUtil.isEmpty(departmentPutVo.getName())) throw new ParamException("科室名称不能为空");
      if (departmentPutVo.getHospitalId()==null)throw new ParamException("该科室没有所属医院");
        if (departmentMapper.isExistName(departmentPutVo.getHospitalId().toString(),departmentPutVo.getName()) > 0) throw new ParamException("科室名称已存在,请重新输入");
        departmentPutVo.setId(Integer.parseInt(id));
        return departmentMapper.update(departmentVoToDepartment(departmentPutVo, PutVo.class));
    }


    /**
     * 将VO对象转为Model
     *
     * @param obj
     * @param voType
     * @return
     */
    private Department departmentVoToDepartment(Object obj, Class<?> voType) {
        mapperFactory.classMap(voType, Department.class)
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Department.class);
    }


}
