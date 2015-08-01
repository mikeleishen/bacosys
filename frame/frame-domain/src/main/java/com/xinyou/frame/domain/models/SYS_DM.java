package com.xinyou.frame.domain.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.BIZ_SYS;
import com.xinyou.frame.domain.entities.ROLE_MAIN;


@XmlRootElement(name = "SYS_DM")
public class SYS_DM {
	private int count;
	private ROLE_MAIN roleData;
	private List<BIZ_SYS> bizSysListData;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ROLE_MAIN getRoleData() {
		return roleData;
	}
	public void setRoleData(ROLE_MAIN roleData) {
		this.roleData = roleData;
	}
	public List<BIZ_SYS> getBizSysListData() {
		return bizSysListData;
	}
	public void setBizSysListData(List<BIZ_SYS> bizSysListData) {
		this.bizSysListData = bizSysListData;
	}
}