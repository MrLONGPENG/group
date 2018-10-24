package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.vo.department.DepartmentVo;
import com.mujugroup.core.objeck.vo.department.PutVo;
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
    private final HospitalMapper hospitalMapper;

    @Override
    public boolean delete(String id) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("科室编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("科室编号必须为数字");
        if (departmentMapper.findById(Integer.parseInt(id)) == null) throw new ParamException("该科室不存在");
        return departmentMapper.deleteById(Integer.parseInt(id));
    }

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper, MapperFactory mapperFactory, HospitalMapper hospitalMapper) {
        this.departmentMapper = departmentMapper;
        this.mapperFactory = mapperFactory;
        this.hospitalMapper = hospitalMapper;
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
        return departmentMapper.getListByHidOrName(hid, name);
    }

    @Override
    public boolean insert(DepartmentVo departmentVo) throws ParamException {
        if (departmentVo.getHid() == null) throw new ParamException("请选择所属医院");
        Hospital hospital = hospitalMapper.findById(departmentVo.getHid());
        if (hospital == null) throw new ParamException("该医院不存在");
        if (StringUtil.isEmpty(departmentVo.getName())) throw new ParamException("科室名称不能为空");
        if (departmentMapper.isExistName(departmentVo.getHid().toString(), departmentVo.getName()) > 0)
            throw new ParamException("该科室已存在");
        departmentVo.setCreateDate(new Date());
        departmentVo.setStatus(1);
        return departmentMapper.insert(departmentVoToDepartment(departmentVo, DepartmentVo.class));
    }

    @Override
    public boolean update(PutVo departmentPutVo) throws ParamException {
        if (departmentPutVo.getId() == null) throw new ParamException("科室编号不能为空");
        Department department=departmentMapper.findById(departmentPutVo.getId());
        if (department == null) throw new ParamException("该科室不存在");
        if (StringUtil.isEmpty(departmentPutVo.getName())) throw new ParamException("科室名称不能为空");
        if (departmentPutVo.getHid() == null) throw new ParamException("请选择所属医院");
        Hospital hospital = hospitalMapper.findById(departmentPutVo.getHid());
        if (hospital == null) throw new ParamException("该医院不存在");
        if (departmentMapper.isExistName(departmentPutVo.getHid().toString(), departmentPutVo.getName()) > 0)
            throw new ParamException("科室名称已存在,请重新输入");
        if (departmentPutVo.getHid() != department.getHospitalId()) {
            throw new ParamException("暂不支持所属医院变更");
        }
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
                .field("hid", "hospitalId")
                .field("moid", "aihuiDepartId")
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Department.class);
    }


}
