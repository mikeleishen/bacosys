package com.xinyou.frame.domain.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.BIZ_SYS;
import com.xinyou.frame.domain.entities.USR_MAIN;

@XmlRootElement(name = "USR_LOGIN_DM")
public class USR_LOGIN_DM {
	private USR_MAIN usrData;
	private List<FUN_NODE> funNodeListData;
	private List<BIZ_SYS> bizSysListData;
	public USR_MAIN getUsrData() {
		return usrData;
	}
	public void setUsrData(USR_MAIN usrData) {
		this.usrData = usrData;
	}
	public List<FUN_NODE> getFunNodeListData() {
		return funNodeListData;
	}
	public void setFunNodeListData(List<FUN_NODE> funNodeListData) {
		this.funNodeListData = funNodeListData;
	}
	public List<BIZ_SYS> getBizSysListData() {
		return bizSysListData;
	}
	public void setBizSysListData(List<BIZ_SYS> bizSysListData) {
		this.bizSysListData = bizSysListData;
	}
}
