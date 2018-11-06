package com.lveqia.cloud.common.objeck.to;


import lombok.Data;

/**
 * Service Transfer Object
 * 请求参数-数据传输对象
 * 代理商ID,医院ID,科室ID
 */
@Data
public class RequestTo {


    private String aid;
    private String hid;
    private String oid;
    private long start;
    private long end;
    private int orderType;

    private String tradeNo;

    private int pageNum;
    private int pageSize;

    public RequestTo(){

    }
    /**
     * 不分页数据
     */
    public RequestTo(String aid, String hid, String oid, int orderType, long start, long end) {
        this(aid, hid, oid, orderType, start, end, 1, 0 );
    }


    public RequestTo(String aid, String hid, String oid, int orderType, long start, long end, int pageNum, int pageSize) {
        this.aid = aid;
        this.hid = hid;
        this.oid = oid;
        this.end = end;
        this.start = start;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderType = orderType;
    }

}
