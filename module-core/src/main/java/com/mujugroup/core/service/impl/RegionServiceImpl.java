package com.mujugroup.core.service.impl;

import com.mujugroup.core.mapper.RegionMapper;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("regionService")
public class RegionServiceImpl implements RegionService {


    private final RegionMapper regionMapper;

    @Autowired
    public RegionServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public List<SelectVO> getRegionByPid(int pid) {
        return regionMapper.getRegionByPid(pid);
    }
}
