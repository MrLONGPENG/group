package com.mujugroup.data.service.impl;

import com.google.gson.JsonObject;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.data.objeck.bo.ExcelBO;
import com.mujugroup.data.objeck.bo.ProfitBO;
import com.mujugroup.data.objeck.bo.sta.StaProfit;
import com.mujugroup.data.objeck.vo.ExcelVO;
import com.mujugroup.data.objeck.vo.ProfitVO;
import com.mujugroup.data.objeck.vo.StaProfitVO;
import com.mujugroup.data.service.ExcelService;
import com.mujugroup.data.service.StaBOService;
import com.mujugroup.data.service.StaVOService;
import com.mujugroup.data.service.feign.ModuleCoreService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Service("staVOService")
public class StaVOServiceImpl implements StaVOService {

    private final ExcelService excelService;
    private final StaBOService staBOService;
    private final MapperFactory mapperFactory;
    private final ModuleCoreService moduleCoreService;
    @Autowired
    public StaVOServiceImpl(ExcelService excelService, StaBOService staBOService
            , MapperFactory mapperFactory, ModuleCoreService moduleCoreService) {
        this.excelService = excelService;
        this.staBOService = staBOService;
        this.mapperFactory = mapperFactory;
        this.moduleCoreService = moduleCoreService;
    }

    /**
     * BO转VO
     */
    @Override
    public List<?> getStaVOList(String action, String uid, int pid, int cid, String aid, String hid, String oid
            , int grain, int start, int stop) throws BaseException {
       String[] ids =  checkIds(uid, pid, cid, aid, hid, oid);
        switch (action){
            case "getStaUsage": case "get_statistics_usage" :
                return staBOService.getUsage(ids, grain, start, stop);
            case "getStaActive" : case "get_statistics_active" :
                return staBOService.getActive(ids, grain, start, stop);
            case "getStaProfit" : case "get_statistics_profit" :
               List<StaProfit> staProfitList = staBOService.getProfit(ids, grain, start, stop);
                mapperFactory.classMap(StaProfit.class, StaProfitVO.class)
                        .fieldMap("profit").converter("rmbPriceConvert").add()
                        .byDefault().register();
                return mapperFactory.getMapperFacade().mapAsList(staProfitList, StaProfitVO.class);
            case "getStaUsageRate": case "get_statistics_usage_rate" :
                return staBOService.getUsageRate(ids, grain, start, stop);
        }
        throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "无法找到Action:"+action);
    }

    @Override
    public String[] checkIds(String uid, String aid, String hid, String oid) {
        return checkIds(uid, 0,0 , aid, hid, oid);
    }

    /**
     * 根据条件确定真实的AID/HID/OID
     * TODO 此处方法还需要完善
     */
    private String[] checkIds(String uid, int pid, int cid, String aid, String hid, String oid) {
        String[] ids = new String[]{aid, hid, oid};
        if(pid != 0 || cid != 0){ // 优先根据城市查询医院ID集合
            Set<Integer> set =  moduleCoreService.getHospitalByRegion(pid, cid);
            if(set != null && set.size() > 0 ) ids[1] = StringUtil.toLinkByDouHao(set.toArray());
            return  ids;
        }
        Map<String, String> map = moduleCoreService.getAuthData(uid);
        if(Constant.DIGIT_ZERO.equals(aid)){
            if(map.containsKey(CoreConfig.AUTH_DATA_AGENT)) ids[0] = map.get(CoreConfig.AUTH_DATA_AGENT);
        }
        if(Constant.DIGIT_ZERO.equals(hid)){
            if(map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL)) ids[1] = map.get(CoreConfig.AUTH_DATA_HOSPITAL);
        }
        if(Constant.DIGIT_ZERO.equals(oid)){
            if(map.containsKey(CoreConfig.AUTH_DATA_DEPARTMENT)) ids[2] = map.get(CoreConfig.AUTH_DATA_DEPARTMENT);
        }
        return ids;
    }

    @Override
    public ProfitVO getProfitVO(ProfitBO profitBO) {
        mapperFactory.classMap(ProfitBO.class, ProfitVO.class)
                .fieldMap("totalProfit").converter("rmbPriceConvert").add()
                .fieldMap("yesterdayProfit").converter("rmbPriceConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(profitBO, ProfitVO.class);
    }




    @Override
    public List<ExcelVO> getExcelVO(String uid, String hid, int grain, int startTime, int stopTime)
            throws BaseException {
        JsonObject info = excelService.getHospitalJson(hid);
        if(info == null) throw new BaseException(ResultUtil.CODE_REMOTE_CALL_FAIL, null);
        mapperFactory.classMap(ExcelBO.class, ExcelVO.class)
                .fieldMap("profit").converter("rmbPriceConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(staBOService.getExcelBO(info
                , grain, startTime,stopTime), ExcelVO.class);
    }


}
