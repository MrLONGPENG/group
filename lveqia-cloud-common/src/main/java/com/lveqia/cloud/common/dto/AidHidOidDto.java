package com.lveqia.cloud.common.dto;


/**
 * Data Transfer Object
 * 请求参数-数据传输对象
 * 代理商ID,医院ID,科室ID
 */
public class AidHidOidDto {

    private int aid;
    private int hid;
    private int oid;
    private long start;
    private long end;
    private String tradeNo;

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
}
