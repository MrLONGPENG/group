package com.mujugroup.lock.controller;


import com.mujugroup.lock.service.LockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/record")
public class LockRecordController {

    private LockRecordService lockRecordService;

    @Autowired
    public LockRecordController(LockRecordService lockRecordService) {
        this.lockRecordService = lockRecordService;
    }




}
