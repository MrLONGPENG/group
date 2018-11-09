package com.mujugroup.data.service.impl;

import com.google.gson.JsonObject;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.data.objeck.bo.ExcelBo;
import com.mujugroup.data.objeck.bo.ProfitBo;
import com.mujugroup.data.objeck.bo.sta.StaProfit;
import com.mujugroup.data.objeck.vo.ExcelVo;
import com.mujugroup.data.objeck.vo.ProfitVo;
import com.mujugroup.data.objeck.vo.StaProfitVo;
import com.mujugroup.data.service.ExcelService;
import com.mujugroup.data.service.StaBoService;
import com.mujugroup.data.service.StaVoService;
import com.mujugroup.data.service.feign.ModuleCoreService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Service("staVOService")
public class StaVOServiceImpl implements StaVoService {

    private final ExcelService excelService;
    private final StaBoService staBoService;
    private final MapperFactory mapperFactory;
    private final ModuleCoreService moduleCoreService;
    @Autowired
    public StaVOServiceImpl(ExcelService excelService, StaBoService staBoService
            , MapperFactory mapperFactory, ModuleCoreService moduleCoreService) {
        this.excelService = excelService;
        this.staBoService = staBoService;
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
                return staBoService.getUsage(ids, grain, start, stop);
            case "getStaActive" : case "get_statistics_active" :
                return staBoService.getActive(ids, grain, start, stop);
            case "getStaProfit" : case "get_statistics_profit" :
               List<StaProfit> staProfitList = staBoService.getProfit(ids, grain, start, stop);
                mapperFactory.classMap(StaProfit.class, StaProfitVo.class)
                        .fieldMap("profit").converter("rmbPriceConvert").add()
                        .byDefault().register();
                return mapperFactory.getMapperFacade().mapAsList(staProfitList, StaProfitVo.class);
            case "getStaUsageRate": case "get_statistics_usage_rate" :
                return staBoService.getUsageRate(ids, grain, start, stop);
        }
        throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "无法找到Action:"+action);
    }

    @Override
    public String[] checkIds(String uid, String aid, String hid, String oid) throws DataException {
        return checkIds(uid, 0,0 , aid, hid, oid);
    }

    /**
     * 根据条件确定真实的AID/HID/OID
     * TODO 此处方法还需要完善
     */
    private String[] checkIds(String uid, int pid, int cid, String aid, String hid, String oid) throws DataException {
        String[] ids = new String[]{aid, hid, oid};
        if(pid != 0 || cid != 0){ // 优先根据城市查询医院ID集合
            Set<Integer> set =  moduleCoreService.getHospitalByRegion(pid, cid);
            if(set != null && set.size() > 0 ) ids[1] = StringUtil.toLinkByDouHao(set.toArray());
            return  ids;
        }
        Map<String, String> map = moduleCoreService.getAuthData(uid);
        if(map.size() ==0 ) throw new DataException(DataException.NO_AUTHORITY);
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
    public ProfitVo getProfitVO(ProfitBo profitBO) {
        mapperFactory.classMap(ProfitBo.class, ProfitVo.class)
                .fieldMap("totalProfit").converter("rmbPriceConvert").add()
                .fieldMap("yesterdayProfit").converter("rmbPriceConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(profitBO, ProfitVo.class);
    }




    @Override
    public List<ExcelVo> getExcelVO(String uid, String hid, int grain, int startTime, int stopTime)
            throws BaseException {
        JsonObject info = excelService.getHospitalJson(hid);
        if(info == null) throw new BaseException(ResultUtil.CODE_REMOTE_CALL_FAIL, null);
        mapperFactory.classMap(ExcelBo.class, ExcelVo.class)
                .fieldMap("profit").converter("rmbPriceConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(staBoService.getExcelBO(info
                , grain, startTime,stopTime), ExcelVo.class);
    }


}
