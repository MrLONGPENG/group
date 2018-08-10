package com.mujugroup.wx.bean;

import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.wx.model.WxUsing;

import java.io.Serializable;
import java.util.Date;

/**
 * 使用信息
 */

public class QueryBean implements Serializable {

    private String did;

    private Integer payCost;

    private Long payTime;

    private Long endTime;

    private Date unlockTime;

    private Boolean using;

	private boolean isMidday;


	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public Integer getPayCost() {
		return payCost;
	}

	public void setPayCost(Integer payCost) {
		this.payCost = payCost;
	}

    public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

    public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

    public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

    public Boolean getUsing() {
		return using;
	}

	public void setUsing(Boolean using) {
		this.using = using;
	}


	public boolean isMidday() {
		return isMidday;
	}

	public void setMidday(boolean midday) {
		isMidday = midday;
	}

	public void setWxUsing(WxUsing wxUsing) {
		setUsing(wxUsing.getUsing());
		setEndTime(wxUsing.getEndTime());
		setPayTime(wxUsing.getPayTime());
		setPayCost(wxUsing.getPayCost());
		setUnlockTime(wxUsing.getUnlockTime());
		setDid(StringUtil.autoFillDid(wxUsing.getDid()));
	}
}