package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.vo.department.AddVo;
import com.mujugroup.core.objeck.vo.department.ListVo;
import com.mujugroup.core.objeck.vo.department.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.AuthDataService;
import com.mujugroup.core.service.DepartmentService;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentMapper departmentMapper;
    private final MapperFactory mapperFactory;
    private final HospitalMapper hospitalMapper;
    private final AuthDataService authDataService;
    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Override
    public boolean delete(int uid, String id) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL)) throw new DataException("当前用户无最高数据权限，暂无法进行删除操作!");
        if (StringUtil.isEmpty(id)) throw new ParamException("科室编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("科室编号必须为数字");
        if (departmentMapper.findById(Integer.parseInt(id)) == null) throw new ParamException("该科室不存在");
        return departmentMapper.deleteById(Integer.parseInt(id));
    }

    @Override
    public List<ListVo> findAll(int uid, int hid, String name) throws DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL)) throw new DataException("当前用户无最高数据权限，暂无法查看数据!");
        return departmentMapper.findAll(hid, name);
    }

    @Override
    public List<SelectVO> getSelectList(int hid, String name) {
        return departmentMapper.getSelectList(hid, name);
    }

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper, MapperFactory mapperFactory, HospitalMapper hospitalMapper, AuthDataService authDataService) {
        this.departmentMapper = departmentMapper;
        this.mapperFactory = mapperFactory;
        this.hospitalMapper = hospitalMapper;
        this.authDataService = authDataService;
    }

    @Override
    public List<DBMap> findOidByHid(String hid) {
        return departmentMapper.findOidByHid(hid);
    }

    //ToDo:待完善
    @Override
    public boolean insert(int uid, AddVo departmentVo) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL)) // TODO 后续不能如此简单判断
            throw new DataException("当前用户没有最高数据权限，暂无法进行科室的添加操作!");
        Hospital hospital = hospitalMapper.findById(departmentVo.getHid());
        if (hospital == null) throw new ParamException("该医院不存在");
        if (departmentMapper.isExistName(departmentVo.getHid().toString(), departmentVo.getName()) > 0)
            throw new ParamException("该科室已存在");
        departmentVo.setCreateDate(new Date());
        departmentVo.setStatus(1);
        return departmentMapper.insert(departmentVoToDepartment(departmentVo, AddVo.class));
    }

    @Override
    public boolean update(int uid, PutVo departmentPutVo) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL))
            throw new DataException("当前用户没有最高数据权限，暂无法进行科室的修改操作!");
        if (departmentPutVo.getId() == null) throw new ParamException("科室编号不能为空");
        Department department = departmentMapper.findById(departmentPutVo.getId());
        if (department == null) throw new ParamException("该科室不存在");
        if (StringUtil.isEmpty(departmentPutVo.getName())) throw new ParamException("科室名称不能为空");
        if (departmentPutVo.getHid() == null) throw new ParamException("请选择所属医院");
        Hospital hospital = hospitalMapper.findById(departmentPutVo.getHid());
        if (hospital == null) throw new ParamException("该医院不存在");
        if (departmentMapper.isExistName(departmentPutVo.getHid().toString(), departmentPutVo.getName()) > 0)
            throw new ParamException("科室名称已存在,请重新输入");
        if (!departmentPutVo.getHid().equals(department.getHid())) {
            throw new ParamException("暂不支持所属医院变更");
        }
        return departmentMapper.update(departmentVoToDepartment(departmentPutVo, PutVo.class));
    }


    /**
     * 将VO对象转为Model
     */
    private Department departmentVoToDepartment(Object obj, Class<?> voType) {
        mapperFactory.classMap(voType, Department.class).byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Department.class);
    }


}
