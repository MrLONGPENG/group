package com.lveqia.cloud.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonUtil {
    private static Gson gson = new GsonBuilder().setExclusionStrategies().create();

    public static JsonObject toJsonObject(String json) {
        return gson.fromJson(json, JsonObject.class);
    }
}
