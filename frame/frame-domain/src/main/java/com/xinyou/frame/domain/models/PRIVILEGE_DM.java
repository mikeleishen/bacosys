package com.xinyou.frame.domain.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.PRIVILEGE_MAIN;

@XmlRootElement(name = "PRIVILEGE_DM")
public class PRIVILEGE_DM {
	private int count;
	private List<PRIVILEGE_MAIN> privilegeListData;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<PRIVILEGE_MAIN> getPrivilegeListData() {
		return privilegeListData;
	}
	public void setPrivilegeListData(List<PRIVILEGE_MAIN> privilegeListData) {
		this.privilegeListData = privilegeListData;
	}
}