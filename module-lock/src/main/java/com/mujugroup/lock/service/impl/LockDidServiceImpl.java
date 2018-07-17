package com.mujugroup.lock.service.impl;


import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.lock.mapper.LockDidMapper;
import com.mujugroup.lock.model.LockDid;
import com.mujugroup.lock.service.LockDidService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Service("lockDidService")
public class LockDidServiceImpl implements LockDidService {

    private final LockDidMapper lockDidMapper;
    private final Logger logger = LoggerFactory.getLogger(LockDidServiceImpl.class);
    @Autowired
    public LockDidServiceImpl(LockDidMapper lockDidMapper) {
        this.lockDidMapper = lockDidMapper;
    }


    @Override
    @Cacheable(value = "lock_did_did", key = "#did", unless="#result == null")
    public LockDid getLockDidByDid(String did) {
        logger.debug("getLockDidByDid");
        return lockDidMapper.getLockDidByDid(did);
    }

    @Override
    @Cacheable(value = "lock_did_bid", key = "#bid", unless="#result == null")
    public LockDid getLockDidByBid(String bid) {
        logger.debug("getLockDidByBid");
        return lockDidMapper.getLockDidByBid(bid);
    }

    @Override
    @Transactional
    public boolean batchInsert(List<LockDid> list) {
        if(list == null || list.size()==0) return false;
        for (LockDid lockDid : list) {
            lockDidMapper.insert(lockDid);
        }
        return true;
    }



    @Override
    public List<LockDid> readExcel(MultipartFile file, String fileName, int brand
            , int didCell, int bidCell) throws IOException {
        if(fileName==null) return null;
        if (!fileName.matches("^.+\\.(?i)(xls)$")
                && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return null;
        }
        InputStream is = file.getInputStream();
        Workbook wb;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            wb = new XSSFWorkbook(is);
        } else {
            wb = new HSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        Row row;
        String did, bid;
        List<LockDid> list = new ArrayList<>();
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            row = sheet.getRow(r);
            if (row == null) continue;
            if(row.getPhysicalNumberOfCells() < bidCell) continue;
            did = getValue(row.getCell(didCell));
            bid = getValue(row.getCell(bidCell));
            if(!StringUtil.isNumeric(did) || !StringUtil.isNumeric(bid)) continue;
            list.add(createLockDid(Long.parseLong(did), Long.parseLong(bid), brand));
        }
        return list;
    }

    private LockDid createLockDid(long did, long bid, int brand) {
        LockDid lockDid = new LockDid();
        lockDid.setBrand(brand);
        lockDid.setDid(did);
        lockDid.setLockId(bid);
        lockDid.setLockHex(Long.toHexString(bid));
        return lockDid;
    }


    private String getValue(Cell cell) {
        if(cell!=null) {
            if (cell.getCellTypeEnum() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if(cell.getCellTypeEnum() == CellType.BOOLEAN){
                return String.valueOf(cell.getBooleanCellValue());
            }  else if(cell.getCellTypeEnum() == CellType.FORMULA){
                return String.valueOf(cell.getCellFormula());
            } else if(cell.getCellTypeEnum() == CellType.BLANK){
                return String.valueOf(cell.getStringCellValue());
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                return new DecimalFormat("#").format(cell.getNumericCellValue());
            }
        }
        return null;
    }

}
