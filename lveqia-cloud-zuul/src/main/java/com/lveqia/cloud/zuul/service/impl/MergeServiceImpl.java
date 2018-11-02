package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.zuul.mapper.SysUserMapper;
import com.lveqia.cloud.zuul.service.MergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service(value = "mergeService")
public class MergeServiceImpl implements MergeService {
    private final SysUserMapper sysUserMapper;

    @Autowired
    public MergeServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public Map<String, String> getNameByUid(String param) {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String id : array) {
            if (StringUtil.isEmpty(id)) continue;
            hashMap.put(id, sysUserMapper.getNameById(id));
        }
        return hashMap;

    }
}
