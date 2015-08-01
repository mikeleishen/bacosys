package com.xinyou.frame.domain.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.ROLE_MAIN;

import java.util.List;

@XmlRootElement(name = "ROLE_DM")
public class ROLE_DM {
	private int count;
	private List<ROLE_MAIN> roleListData;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<ROLE_MAIN> getRoleListData() {
		return roleListData;
	}
	public void setRoleListData(List<ROLE_MAIN> roleListData) {
		this.roleListData = roleListData;
	}
}