package com.mujugroup.lock.service.impl;

import com.mujugroup.lock.service.ReceiveService;
import com.mujugroup.lock.service.task.ReceiveTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {
    private final ReceiveTask receiveTask;
    @Autowired
    public ReceiveServiceImpl(ReceiveTask receiveTask) {
        this.receiveTask = receiveTask;
    }

    @Override
    public void receive(String info){
        this.receiveTask.doReceiveData(info);
    }
}
