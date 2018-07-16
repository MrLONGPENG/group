package com.mujugroup.lock.service;


import com.google.gson.JsonObject;

public interface DeviceService {

    String didToBid(String did);

    JsonObject unlock(String did);

    JsonObject query(String did);

    JsonObject beep(String did);

    JsonObject ble(String did);
}
