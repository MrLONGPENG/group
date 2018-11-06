package com.lveqia.cloud.common.objeck.to;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 *  Service Transfer Object
 * 分页数据-传输对象
 * @param <T>
 */
public class PageTo<T> {

    private PageInfo pageInfo;

    private List<T> pageList;

    /** 远程调用空参数构造函数为必须 */
    public PageTo(){}

    public PageTo(PageInfo pageInfo, List<T> pageList) {
        this.pageInfo = pageInfo;
        this.pageList = pageList;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
}
