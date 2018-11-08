package com.mujugroup.lock.service.impl;

import com.mujugroup.lock.service.ReceiveService;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;


@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {
    private final Logger logger = LoggerFactory.getLogger(ReceiveServiceImpl.class);
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private Queue queue = new ActiveMQQueue("record");


    @Autowired
    public ReceiveServiceImpl(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;

    }

    @Override
    public void receive(String info){
        try {
            this.jmsMessagingTemplate.convertAndSend(queue, info);
        } catch (Exception e) {
            logger.warn("JMS无法发送消息");
        }
    }
}
