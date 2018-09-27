package com.mujugroup.core.objeck.bo;


import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.core.service.MergeService;

public class TreeBO {

    private String id;
    private String name;
    private boolean disabled;

    @MergeField(feign = MergeService.class, method = "getAuthTree"
            , isValueNeedMerge = true, defaultValue = Constant.JSON_ARRAY)
    private String children;

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

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }
}
