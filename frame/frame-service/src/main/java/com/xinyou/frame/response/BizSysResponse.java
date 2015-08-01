package com.xinyou.frame.response;

import java.util.List;

import com.xinyou.frame.domain.entities.BIZ_SYS;
import com.xinyou.frame.domain.models.SYS_DM;

public class BizSysResponse {
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public BIZ_SYS getBizSysData() {
		return bizSysData;
	}
	public void setBizSysData(BIZ_SYS bizSysData) {
		this.bizSysData = bizSysData;
	}
	public List<BIZ_SYS> getBizSysListData() {
		return bizSysListData;
	}
	public void setBizSysListData(List<BIZ_SYS> bizSysListData) {
		this.bizSysListData = bizSysListData;
	}
	public SYS_DM getBizSysDMData() {
		return bizSysDMData;
	}
	public void setBizSysDMData(SYS_DM bizSysDMData) {
		this.bizSysDMData = bizSysDMData;
	}
	private String status;
	private String info;
	private BIZ_SYS bizSysData;
	private List<BIZ_SYS> bizSysListData;
	private SYS_DM bizSysDMData;
	
	
}
