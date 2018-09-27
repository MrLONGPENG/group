package com.mujugroup.core.objeck.vo;

import com.google.gson.JsonArray;


public class TreeVO {

    private String id;
    private String name;
    private boolean disabled;
    private JsonArray children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public JsonArray getChildren() {
        return children;
    }

    public void setChildren(JsonArray children) {
        this.children = children;
    }
}
