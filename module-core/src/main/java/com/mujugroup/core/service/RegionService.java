package com.mujugroup.core.service;

import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;

/**
 * @author leolaurel
 */
public interface RegionService {


    List<SelectVO> getRegionByPid(int pid);
}
