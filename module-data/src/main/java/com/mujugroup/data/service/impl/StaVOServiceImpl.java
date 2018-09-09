package com.mujugroup.data.service.impl;

import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.data.objeck.bo.sta.StaProfit;
import com.mujugroup.data.objeck.vo.StaProfitVO;
import com.mujugroup.data.service.StaBOService;
import com.mujugroup.data.service.StaVOService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("staVOService")
public class StaVOServiceImpl implements StaVOService {

    private final StaBOService staBOService;
    private final MapperFactory mapperFactory;

    @Autowired
    public StaVOServiceImpl(StaBOService staBOService, MapperFactory mapperFactory) {
        this.staBOService = staBOService;
        this.mapperFactory = mapperFactory;
    }

    /**
     * BO转VO
     */
    @Override
    public List<?> getStaVOList(String action, String ids, int aid, int hid, int oid, int grain, int start, int stop)
            throws BaseException, ParamException {
        switch (action){
            case "getStaUsage": case "get_statistics_usage" :
                return staBOService.getUsage(ids, aid, hid, oid, grain, start, stop);
            case "getStaActive" : case "get_statistics_active" :
                return staBOService.getActive(ids, aid, hid, oid, grain, start, stop);
            case "getStaProfit" : case "get_statistics_profit" :
                return staBOService.getProfit(ids, aid, hid, oid, grain, start, stop);
//                List<StaProfit> staProfitList = staBOService.getProfit(ids, aid, hid, oid, grain, start, stop);
//                mapperFactory.classMap(StaProfit.class, StaProfitVO.class)
//                        .byDefault().register();
//                return mapperFactory.getMapperFacade().mapAsList(staProfitList, StaProfitVO.class);
            case "getStaUsageRate": case "get_statistics_usage_rate" :
                return staBOService.getUsageRate(ids, aid, hid, oid, grain, start, stop);
        }
        throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "无法找到Action:"+action);
    }
}
