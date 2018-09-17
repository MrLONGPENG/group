package com.lveqia.cloud.common.objeck;

import java.util.HashMap;


/**
 * 构建数据库返回Map结构数据对象
 */
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
