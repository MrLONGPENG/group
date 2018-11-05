package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.zuul.mapper.SysUserMapper;
import com.lveqia.cloud.zuul.service.MergeService;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        param = param.replaceAll(Constant.SIGN_FEN_HAO, Constant.SIGN_DOU_HAO);
        List<DBMap> list = sysUserMapper.getNameByIds(param);
        list.forEach(dbMap -> dbMap.addTo(hashMap));
        return hashMap;

    }
}
