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

    @Override
    public int updateAuthData(int uid, String[] authDatas) {
        //删除当前用户的所有数据权限
        authDataMapper.deleteByUid(uid);
        return addAuthData(uid, authDatas);
    }

    @Override
    public int addAuthData(int uid, String[] authDatas) {

        int[] ridArray = new int[authDatas.length];
        int[] typeArrary = new int[authDatas.length];
        if (authDatas != null && authDatas.length > 0) {
            String str = null;
            for (int i = 0; i < authDatas.length; i++) {
                //将关系ID放入关系数组中存储
                ridArray[i] = Integer.parseInt(authDatas[i].substring(3));
                //将关系类型加入到类型数组中
                str = authDatas[i].substring(0, 3);
                if (str.equalsIgnoreCase("AID")) {
                    typeArrary[i] = 1;
                } else if (str.equalsIgnoreCase("HID")) {
                    typeArrary[i] = 2;
                } else {
                    typeArrary[i] = 3;
                }
            }
        }
        return authDataMapper.addAuthData(uid, ridArray, typeArrary);
    }

    @Override
    public int deleteByUid(int uid) {
        return authDataMapper.deleteByUid(uid);
    }

    @Autowired
    public AuthDataServiceImpl(AuthDataMapper authDataMapper, MapperFactory mapperFactory) {
        this.authDataMapper = authDataMapper;
        this.mapperFactory = mapperFactory;
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
