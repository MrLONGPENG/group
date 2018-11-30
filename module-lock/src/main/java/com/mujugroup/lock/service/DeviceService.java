package com.mujugroup.lock.service;


public interface DeviceService {

    String didToBid(String did);

    String remoteCall(int type, String did);
}
