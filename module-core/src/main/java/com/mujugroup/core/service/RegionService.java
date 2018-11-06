package com.mujugroup.core.service;

import com.mujugroup.core.objeck.vo.SelectVo;

import java.util.List;

/**
 * @author leolaurel
 */
public interface RegionService {


    List<SelectVo> getRegionByPid(int pid);
}
