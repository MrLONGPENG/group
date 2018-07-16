package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxRelationMapper;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.service.WxRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("wxRelationService")
public class WxRelationServiceImpl implements WxRelationService {


    private final WxRelationMapper wxRelationMapper;

    @Autowired
    public WxRelationServiceImpl(WxRelationMapper wxRelationMapper) {
        this.wxRelationMapper = wxRelationMapper;
    }

    @Override
    public String test() {
        return "hello world!";
    }
}
