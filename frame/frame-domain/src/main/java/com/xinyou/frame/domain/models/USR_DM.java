package com.xinyou.frame.domain.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.xinyou.frame.domain.entities.USR_MAIN;


@XmlRootElement(name = "USR_DM")
public class USR_DM {
	private int count;
	private List<USR_MAIN> usrListData;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<USR_MAIN> getUsrListData() {
		return usrListData;
	}
	public void setUsrListData(List<USR_MAIN> usrListData) {
		this.usrListData = usrListData;
	}
}
