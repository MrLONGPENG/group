package com.mujugroup.core.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.objeck.DBMap;
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
    public int updateAuthData(int uid, String[] authData) {
        //删除当前用户的所有数据权限
        authDataMapper.deleteByUid(uid);
        return addAuthData(uid, authData);
    }

    @Override
    public List<String> getAuthDataList(int uid) {
        return authDataMapper.getAuthDataList(uid);
    }

    @Override
    public List<DBMap> getAuthData(int uid) {
        return authDataMapper.getAuthData(uid);
    }

    @Override
    public int addAuthData(int uid, String[] authData) {
        if (authData.length <= 0) return 0;
        String[] ridArray = new String[authData.length];
        String[] typeArray = new String[authData.length];
        for (int i = 0; i < authData.length; i++) {
            //将关系ID放入关系数组中存储
            ridArray[i] = authData[i].substring(3);
            //将关系类型加入到类型数组中
            switch (authData[i].substring(0, 3)){
                case "ALL" : typeArray[i] = CoreConfig.AUTH_DATA_ALL; break;
                case "AID" : typeArray[i] = CoreConfig.AUTH_DATA_AGENT; break;
                case "HID" : typeArray[i] = CoreConfig.AUTH_DATA_HOSPITAL; break;
                case "OID" : typeArray[i] = CoreConfig.AUTH_DATA_HOSPITAL; break;
                default: {
                    logger.warn("未知数据权限格式：{}", authData[i].substring(0, 3));
                    typeArray[i] = CoreConfig.AUTH_DATA_DEPARTMENT;
                    break;
                }
            }
        }
        return authDataMapper.addAuthData(uid, ridArray, typeArray);
    }

    @Override
    public int deleteByUid(int uid) {
        return authDataMapper.deleteByUid(uid);
    }


    @Override
    @MergeResult
    public List<TreeBO> getAllAgentList() {
        return authDataMapper.getAllAgentList();
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
