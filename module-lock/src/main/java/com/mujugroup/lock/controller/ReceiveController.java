package com.mujugroup.lock.controller;


import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.lock.service.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/receive")
public class ReceiveController {
    private ReceiveService receiveService;

    @Autowired
    public ReceiveController(ReceiveService receiveService) {
        this.receiveService = receiveService;
    }

    @RequestMapping(value = "/data")
    public String receive(@RequestBody String data){
        receiveService.receive(data);
        return ResultUtil.success();
    }

}
