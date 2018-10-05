package com.mujugroup.core.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.mapper.AuthDataMapper;
import com.mujugroup.core.objeck.bo.TreeBO;
import com.mujugroup.core.objeck.vo.TreeVO;
import com.mujugroup.core.service.AuthDataService;
import ma.glasnost.orika.MapperFactory;
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
    //private final Logger logger = LoggerFactory.getLogger(AuthDataServiceImpl.class);
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
    public List<DBMap> getAuthData(int uid) {
        return authDataMapper.getAuthData(uid);
    }

    @Override
    public int addAuthData(int uid, String[] authData) {
        int[] ridArray = new int[authData.length];
        int[] typeArray = new int[authData.length];
        if (authData.length > 0) {
            String str;
            for (int i = 0; i < authData.length; i++) {
                //将关系ID放入关系数组中存储
                ridArray[i] = Integer.parseInt(authData[i].substring(3));
                //将关系类型加入到类型数组中
                str = authData[i].substring(0, 3);
                if (str.equalsIgnoreCase("AID")) {
                    typeArray[i] = 1;
                } else if (str.equalsIgnoreCase("HID")) {
                    typeArray[i] = 2;
                } else {
                    typeArray[i] = 3;
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
