package com.lveqia.cloud.common.to;


/**
 * Service Transfer Object
 * 请求参数-数据传输对象
 * 代理商ID,医院ID,科室ID
 */
public class AidHidOidTO {

    private int aid;
    private int hid;
    private int oid;
    private long start;
    private long end;
    private String tradeNo;

    private int pageNum;
    private int pageSize;

    public AidHidOidTO(){

    }

    public AidHidOidTO(int aid, int hid, int oid, long start, long end, int pageNum, int pageSize) {
        this.aid = aid;
        this.hid = hid;
        this.oid = oid;
        this.start = start;
        this.end = end;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
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
