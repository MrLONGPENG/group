package com.lveqia.cloud.common.objeck.to;


/**
 * Service Transfer Object
 * 请求参数-数据传输对象
 * 代理商ID,医院ID,科室ID
 */
public class AidHidOidTO {


    private String aid;
    private String hid;
    private String oid;
    private long start;
    private long end;
    private int orderType;

    private String tradeNo;

    private int pageNum;
    private int pageSize;

    public AidHidOidTO(){

    }

    /**
     * 不分页数据
     */
    public AidHidOidTO(String aid, String hid, String oid, int orderType, long start, long end) {
        this(aid, hid, oid, orderType, start, end, 1, 0 );
    }


    public AidHidOidTO(String aid, String hid, String oid, int orderType, long start, long end, int pageNum, int pageSize) {
        this.aid = aid;
        this.hid = hid;
        this.oid = oid;
        this.end = end;
        this.start = start;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderType = orderType;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }


    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
