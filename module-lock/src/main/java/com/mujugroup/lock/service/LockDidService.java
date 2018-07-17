package com.mujugroup.lock.service;


import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.lock.model.LockDid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LockDidService {

    LockDid getLockDidByDid(String did);

    LockDid getLockDidByBid(String bid);

    List<LockDid> readExcel(MultipartFile file, String fileName, int brand, int didCell, int bidCell) throws IOException;

    boolean batchInsert(List<LockDid> list);
}
