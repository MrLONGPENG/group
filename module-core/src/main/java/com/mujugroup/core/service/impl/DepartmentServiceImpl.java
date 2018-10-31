package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.config.Constant;
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

import java.util.Arrays;
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
    public List<ListVo> findAll(int uid, String hid, String name) throws DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            if (StringUtil.isEmpty(hid) || Constant.DIGIT_ZERO.equals(hid)) {
                return departmentMapper.findAll(map.get(CoreConfig.AUTH_DATA_HOSPITAL), name);
            } else {
                Hospital hospital = hospitalMapper.findById(Integer.parseInt(hid));
                if (hospital == null) throw new DataException("当前科室所属医院不存在");
                String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
                if (Arrays.stream(strArray).anyMatch(s -> s.equals(hospital.getAgentId()))) {
                    return departmentMapper.findAll(hid, name);
                } else {
                    //校验当前医院是否拥有医院的数据权限
                    if (map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
                        if (noHospitalPermission(map, hospital)) {
                            throw new DataException("当前医院没有数据权限,暂无法进行查看科室的操作");
                        } else {
                            return departmentMapper.findAll(hid, name);
                        }
                    } else {
                        throw new DataException("当前医院没有数据权限,暂无法进行查看科室的操作");
                    }
                }

            }
        } else if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && !map.containsKey(CoreConfig.AUTH_DATA_AGENT)
                && map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
            return hospitalPermission(hid, map, name);
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            return departmentMapper.findAll(hid, name);
        } else {
            throw new DataException("当前医院没有数据权限,暂无法进行查看科室的操作");
        }
    }

    private List<ListVo> hospitalPermission(String hid, Map<String, String> map, String name) throws DataException {
        if (StringUtil.isEmpty(hid)) {
            return departmentMapper.findAll(map.get(CoreConfig.AUTH_DATA_HOSPITAL), name);
        } else {
            Hospital hospital = hospitalMapper.findById(Integer.parseInt(hid));
            if (hospital == null) throw new DataException("当前科室所属医院不存在");
            //校验当前医院是否拥有医院的数据权限
            if (noHospitalPermission(map, hospital)) {
                throw new DataException("当前医院没有数据权限,暂无法进行查看科室的操作");
            } else {
                return departmentMapper.findAll(hid, name);
            }
        }
    }

    @Override
    public List<SelectVO> getSelectList(int uid, int hid, String name) throws DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        Hospital hospital = hospitalMapper.findById(hid);
        if (hospital == null) throw new DataException("当前科室所属医院不存在");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
            if (Arrays.stream(strArray).anyMatch(s -> s.equals(hospital.getAgentId()))) {
                return departmentMapper.getSelectList(hid, name);
            } else {
                //校验当前医院是否拥有医院的数据权限
                if (map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
                    if (noHospitalPermission(map, hospital)) {
                        throw new DataException("当前医院没有数据权限,暂无法进行查看科室的操作");
                    } else {
                        return departmentMapper.getSelectList(hid, name);
                    }
                } else {
                    throw new DataException("当前医院没有数据权限,暂无法进行查看科室的操作");
                }
            }

        } else if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && !map.containsKey(CoreConfig.AUTH_DATA_AGENT)
                && map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
            //校验当前医院是否拥有医院的数据权限
            if (noHospitalPermission(map, hospital)) {
                throw new DataException("当前医院没有数据权限,暂无法进行查看科室的操作");
            } else {
                return departmentMapper.getSelectList(hid, name);
            }
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            return departmentMapper.getSelectList(hid, name);
        } else {
            throw new DataException("当前医院没有数据权限,暂无法进行查看科室的操作");
        }
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
        Hospital hospital = hospitalMapper.findById(departmentVo.getHid());
        if (hospital == null) throw new ParamException("当前科室所属医院不存在");
        //当前用户没有最高数据权限,拥有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            //校验所选医院是否拥有相应的代理商数据权限
            String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
            if (Arrays.stream(strArray).anyMatch((s -> s.equals(hospital.getAgentId())))) {
                return addFunc(departmentVo);
            } else {
                if (map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
                    if (noHospitalPermission(map, hospital)) {
                        throw new DataException("当前医院没有数据权限,暂无法进行添加科室的操作");
                    } else {
                        return addFunc(departmentVo);
                    }
                }
            }
        } else if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && !map.containsKey(CoreConfig.AUTH_DATA_AGENT) && map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
            if (noHospitalPermission(map, hospital)) {
                throw new DataException("当前医院没有数据权限,暂无法进行添加科室的操作");
            } else {
                return addFunc(departmentVo);
            }
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            return addFunc(departmentVo);
        } else {
            throw new DataException("当前用户没有操作科室的数据权限,请联系管理员");
        }
        throw new DataException("当前用户没有操作科室的数据权限,请联系管理员");
    }

    private boolean addFunc(AddVo addVo) throws ParamException {
        Hospital hospital = hospitalMapper.findById(addVo.getHid());
        if (hospital == null) throw new ParamException("该医院不存在");
        if (departmentMapper.isExistName(addVo.getName(),String.valueOf(addVo.getHid())) > 0)
            throw new ParamException("该科室已存在");
        addVo.setCreateDate(new Date());
        addVo.setStatus(1);
        return departmentMapper.insert(departmentVoToDepartment(addVo, AddVo.class));
    }

    private boolean modifyFunc(PutVo departmentPutVo) throws ParamException {
        Department department = departmentMapper.findById(departmentPutVo.getId());
        if (department == null) throw new ParamException("该科室不存在");
        if (departmentPutVo.getHid()==null)throw new ParamException("所属医院不能为空");
        Hospital hospital = hospitalMapper.findById(departmentPutVo.getHid());
        if (hospital == null) throw new ParamException("该医院不存在");
        if (department.getName() != null && departmentPutVo.getName() != null) {
            if (!department.getName().equals(departmentPutVo.getName())) {
                if (departmentMapper.isExistName(departmentPutVo.getName(),String.valueOf(departmentPutVo.getHid())) > 0)
                    throw new ParamException("科室名称已存在,请重新输入");
            }
        }
        if (!departmentPutVo.getHid().equals(department.getHid())) {
            throw new ParamException("暂不支持所属医院变更");
        }
        return departmentMapper.update(departmentVoToDepartment(departmentPutVo, PutVo.class));
    }


    @Override
    public boolean update(int uid, PutVo departmentPutVo) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        //通过当前科室获取所属医院并获取所属代理商
        Hospital hospital = hospitalMapper.findById(departmentPutVo.getHid());
        if (hospital == null) throw new ParamException("当前科室所属医院不存在");
        //当前用户没有最高数据权限,拥有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            //校验所选医院是否拥有相应的代理商数据权限
            String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
            if (Arrays.stream(strArray).anyMatch(s -> s.equals(hospital.getAgentId()))) {
                return modifyFunc(departmentPutVo);
            } else {
                if (map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
                    //校验当前医院是否拥有医院的数据权限
                    if (noHospitalPermission(map, hospital)) {
                        throw new DataException("当前医院没有数据权限,暂无法进行更新科室的操作");
                    } else {
                        return modifyFunc(departmentPutVo);
                    }
                } else {
                    throw new DataException("当前用户没有操作科室的数据权限,请联系管理员");
                }
            }
        } else if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && !map.containsKey(CoreConfig.AUTH_DATA_AGENT) && map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
            //校验所选医院是否拥有相应的医院数据权限
            return modifyHospitalPermission(map, hospital, departmentPutVo);
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            return modifyFunc(departmentPutVo);
        } else {
            throw new DataException("当前用户没有操作科室的数据权限,请联系管理员");
        }
    }

    private boolean modifyHospitalPermission(Map<String, String> map, Hospital hospital, PutVo departmentPutVo) throws DataException, ParamException {
        //校验当前医院是否拥有医院的数据权限

        if (noHospitalPermission(map, hospital)) {
            throw new DataException("当前医院没有数据权限,暂无法进行更新科室的操作");
        } else {
            return modifyFunc(departmentPutVo);
        }
    }

    private boolean noHospitalPermission(Map<String, String> map, Hospital hospital) {
        String[] hosArray = map.get(CoreConfig.AUTH_DATA_HOSPITAL).split(Constant.SIGN_DOU_HAO);
        return Arrays.stream(hosArray).anyMatch(s -> s.equals(hospital.getId()));
    }

    @Override
    public boolean delete(int uid, String id) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        if (StringUtil.isEmpty(id)) throw new ParamException("科室编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("科室编号必须为数字");
        Department department = departmentMapper.findById(Integer.parseInt(id));
        if (department == null) throw new ParamException("该科室不存在");
        Hospital hospital = hospitalMapper.findById(department.getHid());
        if (hospital == null) throw new ParamException("当前科室所属医院不存在");
        //当前用户没有最高数据权限,拥有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            //校验所选医院是否拥有相应的代理商数据权限
            String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
            if (Arrays.stream(strArray).anyMatch(s -> s.equals(hospital.getAgentId()))) {
                   return departmentMapper.deleteById(Integer.parseInt(id));
            } else {
                //校验当前医院是否拥有医院的数据权限
                if (map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
                    if (noHospitalPermission(map, hospital)) {
                        throw new DataException("当前医院没有数据权限,暂无法进行删除科室的操作");
                    } else {
                        return departmentMapper.deleteById(Integer.parseInt(id));
                    }
                } else {
                    throw new DataException("当前用户没有操作科室的数据权限,请联系管理员");
                }
            }
        } else if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && !map.containsKey(CoreConfig.AUTH_DATA_AGENT)
                && map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) {
            //当前用户有且仅有医院的数据权限
            return delHospitalPermission(map, hospital, id);
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            //当前用户拥有最高数据权限
            return hospitalMapper.deleteById(Integer.parseInt(id));
        }
        throw new DataException("当前用户没有操作科室的数据权限,请联系管理员");
    }

    private boolean delHospitalPermission(Map<String, String> map, Hospital hospital, String id) throws DataException {
        if (noHospitalPermission(map, hospital)) {
            throw new DataException("当前医院没有数据权限,暂无法进行删除科室的操作");
        } else {
            return departmentMapper.deleteById(Integer.parseInt(id));
        }
    }

    /**
     * 将VO对象转为Model
     */
    private Department departmentVoToDepartment(Object obj, Class<?> voType) {
        mapperFactory.classMap(voType, Department.class).byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Department.class);
    }


}
