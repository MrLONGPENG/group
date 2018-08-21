package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.wx.mapper.WxOrderMapper;
import com.mujugroup.wx.mapper.WxUserMapper;
import com.mujugroup.wx.service.MergeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("mergeService")
public class MergeServiceImpl implements MergeService {

    private final WxUserMapper wxUserMapper;
    private final WxOrderMapper wxOrderMapper;
    private final Logger logger = LoggerFactory.getLogger(MergeServiceImpl.class);

    public MergeServiceImpl(WxUserMapper wxUserMapper, WxOrderMapper wxOrderMapper) {
        this.wxUserMapper = wxUserMapper;
        this.wxOrderMapper = wxOrderMapper;
    }


    /**
     * 根据条件获取全部的用户
     * @param param 代理商ID,时间戳(截至时间)
     */
    @Override
    public Map<String, String> getTotalUserCount(String param) {
        logger.debug("getTotalUserCount->{}", param);
        String[] params = param.split(Constant.SIGN_COMMA);
        Map<String,String> map = new HashMap<>();
        map.put(Constant.DIGIT_ZERO, wxUserMapper.getTotalUserCount(Constant.DIGIT_ZERO, params[1]));
        return map;
    }

    /**
     * 根据条件获取昨天的使用情况
     * @param param 代理商ID,时间戳(截至时间)
     */
    @Override
    public Map<String, String> getYesterdayUsageCount(String param) {
        logger.debug("getYesterdayUsageCount->{}", param);
        String[] params = param.split(Constant.SIGN_COMMA);
        Map<String,String> map = new HashMap<>();
        long start = Long.parseLong(params[1]) - Constant.TIMESTAMP_DAYS_1;
        map.put(Constant.DIGIT_ZERO, wxOrderMapper.getYesterdayUsageCount(params[0], String.valueOf(start), params[1]));
        return map;
    }


}
