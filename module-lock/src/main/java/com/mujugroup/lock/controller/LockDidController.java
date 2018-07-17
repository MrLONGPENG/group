package com.mujugroup.lock.controller;

import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.lock.model.LockDid;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.service.LockDidService;
import com.mujugroup.lock.service.LockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/did")
public class LockDidController {

    private final LockDidService lockDidService;
    private final LockInfoService lockInfoService;

    private final Logger logger = LoggerFactory.getLogger(LockDidController.class);
    @Autowired
    public LockDidController(LockDidService lockDidService, LockInfoService lockInfoService) {
        this.lockDidService = lockDidService;
        this.lockInfoService = lockInfoService;
    }

    @RequestMapping(value = "/getDeviceId")
    public String getDeviceId(String did){
        logger.debug("getDeviceId:" + did);
        return query(did, null);
    }

    @RequestMapping(value = "/query")
    public String query(String did, String bid){
        logger.debug("did:"+did +"  bid:"+bid);
        if(did == null && bid ==null) return ResultUtil.error(ResultUtil.CODE_PARAMETER_MISS);
        LockDid lockDid;
        if(did!=null){
            if(did.length()>9) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
            lockDid = lockDidService.getLockDidByDid(StringUtil.autoFillDid(did));
        }else {
            lockDid = lockDidService.getLockDidByBid(bid);
        }
        if(lockDid == null) return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        return ResultUtil.success(lockDid);
    }

    @RequestMapping(value = "/status")
    public String getStatus(String did){
        logger.debug("getStatus");
        if(!StringUtil.isNumeric(did)) return null;
        LockDid lockDid = lockDidService.getLockDidByDid(StringUtil.autoFillDid(did));
        if(lockDid == null) return null;
        LockInfo lockInfo = lockInfoService.getLockInfoByDid(String.valueOf(lockDid.getLockId()));
        if(lockInfo == null) return null;
        return String.valueOf(lockInfo.getLockStatus());
    }


    @RequestMapping(value = "/bid")
    public String bidToBid(String bid){
        logger.debug("didToBid");
        if(bid == null) return null;
        LockDid lockDid = lockDidService.getLockDidByBid(bid);
        if(lockDid == null) return null;
        return StringUtil.autoFillDid(lockDid.getDid());
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String onImport(@RequestParam("file")MultipartFile file
            , @RequestParam(name="brand", required=false, defaultValue="1")int brand
            , @RequestParam(name="didCell", required=false, defaultValue="1")int didCell
            , @RequestParam(name="bidCell", required=false, defaultValue="3")int bidCell) {
        List<LockDid> list;
        try {
            list = lockDidService.readExcel(file, file.getOriginalFilename(), brand, didCell, bidCell);
        } catch (Exception e) {
            return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "Excel文件格式错误");
        }
        try {
            if(lockDidService.batchInsert(list)){
                return ResultUtil.success();
            }else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL, "数据为空");
            }
        } catch (Exception e) {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL, "事务回滚");
        }
    }


}
