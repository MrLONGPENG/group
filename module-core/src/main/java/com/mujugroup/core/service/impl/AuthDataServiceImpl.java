package com.mujugroup.core.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mujugroup.core.mapper.AuthDataMapper;
import com.mujugroup.core.objeck.bo.TreeBO;
import com.mujugroup.core.objeck.vo.TreeVO;
import com.mujugroup.core.service.AuthDataService;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("authDataService")
public class AuthDataServiceImpl implements AuthDataService {


    private final AuthDataMapper authDataMapper;
    private final MapperFactory mapperFactory;
    private final Logger logger = LoggerFactory.getLogger(AuthDataServiceImpl.class);
    private Gson gson = new GsonBuilder().create();

    @Autowired
    public AuthDataServiceImpl(AuthDataMapper authDataMapper, MapperFactory mapperFactory) {
        this.authDataMapper = authDataMapper;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public int addAuthData(int uid, int[] ids, int[] types) {
        return authDataMapper.addAuthData(uid,ids,types);
    }

    @Override
    @MergeResult
    public List<TreeBO> getAgentAuthData(long id) {
        return authDataMapper.getAgentAuthData(id);
    }

    @Override
    @MergeResult
    public List<TreeBO> getHospitalAuthData(long id) {
        return authDataMapper.getHospitalAuthData(id);
    }


    @Override
    @MergeResult
    public List<TreeBO> getDepartmentAuthData(long id) {
        return authDataMapper.getDepartmentAuthData(id);
    }


    @Override
    @MergeResult
    public List<TreeBO> getAuthTreeByAid(String aid) {
        return authDataMapper.getAuthTreeByAid(aid);
    }

    @Override
    @MergeResult
    public List<TreeBO> getAuthTreeByHid(String hid) {
        return authDataMapper.getAuthTreeByHid(hid);
    }

    @Override
    public List<TreeVO> treeBoToVo(List<TreeBO> list) {
        mapperFactory.classMap(TreeBO.class, TreeVO.class)
                .fieldMap("children").converter("strToJsonArray").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(list, TreeVO.class);
    }

    @Override
    public String toJsonString(List<TreeBO> list) {
        return gson.toJson(treeBoToVo(list));
    }




}
