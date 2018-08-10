package com.mujugroup.lock.service;


import com.mujugroup.lock.model.LockDid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LockDidService {

    LockDid getLockDidByDid(String did);

    LockDid getLockDidByBid(String bid);

    boolean deleteByDid (String did);

    boolean deleteByBid(String bid);

    boolean batchInsert(List<LockDid> list);

    List<LockDid> onBatch(String did, String bid, int brand, int count, boolean isHex);

    List<LockDid> readExcel(MultipartFile file, String fileName, int brand, int didCell, int bidCell
            , boolean isHex) throws IOException;


}
