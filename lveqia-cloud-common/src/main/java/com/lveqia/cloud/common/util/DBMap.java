package com.lveqia.cloud.common.util;

import java.util.HashMap;

public class DBMap {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addTo(HashMap<String,String> hashMap) {
        hashMap.put(key, value);
    }
}
