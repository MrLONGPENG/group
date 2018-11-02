package com.mujugroup.lock.service.impl;

import com.google.gson.JsonObject;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.service.LockInfoService;
import com.mujugroup.lock.service.ReceiveService;
import com.mujugroup.lock.service.feign.ModuleWxService;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.jms.Queue;


@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {
    private final Logger logger = LoggerFactory.getLogger(ReceiveServiceImpl.class);
    private final RestTemplate restTemplate;
    private final LockInfoService lockInfoService;
    private final ModuleWxService moduleWxService;
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private Queue queue = new ActiveMQQueue("lockRecord");


    @Autowired
    public ReceiveServiceImpl(RestTemplate restTemplate, LockInfoService lockInfoService
            , ModuleWxService moduleWxService, JmsMessagingTemplate jmsMessagingTemplate) {
        this.restTemplate = restTemplate;
        this.lockInfoService = lockInfoService;
        this.moduleWxService = moduleWxService;
        this.jmsMessagingTemplate = jmsMessagingTemplate;

    }

    @Override
    public String receive(String info) {
        try {
            this.jmsMessagingTemplate.convertAndSend(queue, info);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
